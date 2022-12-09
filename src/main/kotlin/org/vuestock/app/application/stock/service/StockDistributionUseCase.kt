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
        val bodyJson = distributionApi.requestDistributions(kisApiHeader, stockCode, date)
        if (!bodyJson.containsKey("output1") || !bodyJson.containsKey("output2")) {
            return StockDistribution(
                currentPrice = StockPrice.empty(),
                distributions = emptyArray()
            )
        }
        val currentStock = stockCurrentPriceParser.parse(bodyJson)
        val distributions = parse(bodyJson)
        return StockDistribution(
            currentStock,
            distributions
        )
    }

    override fun parse(rawJsonBody: Map<*, *>): Array<StockPrice> {
        val distributions = rawJsonBody["output2"] as List<*>
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd hhmmss")
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
        }.toTypedArray()
    }
}