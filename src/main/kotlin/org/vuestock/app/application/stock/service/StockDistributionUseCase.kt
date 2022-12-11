package org.vuestock.app.application.stock.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.vuestock.app.adapter.stock.out.StockDistributionExternal
import org.vuestock.app.adapter.stock.out.command.KisApiHeader
import org.vuestock.app.application.stock.port.`in`.StockAuthorizationResolver
import org.vuestock.app.application.stock.port.`in`.StockDistributionPort
import org.vuestock.app.application.stock.port.`in`.StockCurrentPriceParser
import org.vuestock.app.application.stock.port.`in`.StockDistributionParser
import org.vuestock.app.application.stock.port.`in`.dto.StockDistribution
import org.vuestock.app.application.stock.port.`in`.dto.StockPrice
import org.vuestock.app.application.stock.type.StockSign
import org.vuestock.app.infrastructure.common.DateUtils
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

@Service
class StockDistributionUseCase(
    private val distributionApi: StockDistributionExternal,
    private val stockAuthorizationResolverImpl: StockAuthorizationResolver,
    private val stockCurrentPriceParser: StockCurrentPriceParser,
    @Value("\${kis.distribution-trid}")
    private val distributionTrId: String
) : StockDistributionPort, StockDistributionParser {

    override fun getDistribution(stockCode: String, date: LocalDateTime): StockDistribution {
        val authInfo = stockAuthorizationResolverImpl.getAuthorizationInfo()
        val kisApiHeader = KisApiHeader.from(authInfo!!, distributionTrId)
        var distributions: List<StockPrice> = mutableListOf()
        var bd: LocalDateTime
        if (DateUtils.isWeekend(date)) {
            bd = DateUtils.getRecentWeekdayFromWeekend(date)
            bd = bd.withHour(15).withMinute(30)
        }
        else {
            bd = date
        }
        var currentStock: StockPrice? = null
        while (bd.hour >= 9) {
            val bodyJson = distributionApi.requestDistributions(kisApiHeader, stockCode, bd)
            if (!bodyJson.containsKey("output1") || !bodyJson.containsKey("output2")) {
                return StockDistribution(
                    currentPrice = StockPrice.empty(),
                    distributions = emptyList()
                )
            }
            if (currentStock == null) {
                currentStock = stockCurrentPriceParser.parse(bodyJson)
            }
            val parsedDistributions = parse(bodyJson)
            distributions = distributions.plus(parsedDistributions)
            bd = bd.minusMinutes(30)
        }
        return StockDistribution(
            currentStock,
            distributions.reversed()
        )
    }

    override fun parse(rawJsonBody: Map<*, *>): List<StockPrice> {
        val distributions = rawJsonBody["output2"] as List<*>
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd HHmmss")
        return distributions.map {
            val map = it as Map<*, *>
            val date = map["stck_bsop_date"] as String
            val time = map["stck_cntg_hour"] as String
            val price = (map["stck_prpr"] as String).toInt()
            val timeMills = simpleDateFormat.parse("$date $time") ?: Date(-1)
            StockPrice(
                previousDayPriceDiff = 0,
                previousDayPriceDiffPercent = 0.0f,
                previousDaySign = StockSign.None,
                currentPrice = price,
                time = time,
                date = date,
                timeMills = timeMills.time
            )
        }.reversed()
    }
}