package org.vuestock.app.adapter.stock.`in`

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.vuestock.app.adapter.stock.`in`.dto.StockDistributionResponse
import org.vuestock.app.application.stock.port.`in`.StockCurrentPricePort
import org.vuestock.app.application.stock.port.`in`.StockDistributionPort
import org.vuestock.app.application.stock.port.`in`.dto.StockPrice
import org.vuestock.app.application.stock.type.StockSign
import org.vuestock.app.infrastructure.common.CommonResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@RestController
@RequestMapping("/api/v1/stock")
class StockController constructor(
    private val stockCurrentPricePort: StockCurrentPricePort,
    private val stockDistributionPort: StockDistributionPort,
) {

    @GetMapping
    fun getCurrentStockPrice(@RequestParam("stockCode") stockCode: String): CommonResponse<StockPrice> {
        val stockPrice = stockCurrentPricePort.getCurrentPrice(stockCode)
        if (stockPrice.previousDaySign == StockSign.None) {
            return CommonResponse(
                "현재 주가를 알 수 없습니다",
                "404",
                stockPrice
            )
        }
        return CommonResponse(
            "현재 주가 정보를 성공적으로 조회하였습니다.",
            "200",
            stockPrice
        )
    }

    @GetMapping("/distributions")
    fun getStockDistribution(@RequestParam("stockCode") stockCode: String,
                             @RequestParam("dateMills") dateMills: Long):
            CommonResponse<StockDistributionResponse> {
        val time = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateMills), ZoneOffset.systemDefault())
        val stockDistribution = stockDistributionPort.getDistribution(stockCode, time)
        if (stockDistribution.distributions.isEmpty()) {
            return CommonResponse(
                "분봉 데이터 조회에 실패하였습니다",
                "404",
                StockDistributionResponse(stockDistribution, 0L)
            )
        }
        val lastViewTimeMills =
            stockDistribution.distributions[stockDistribution.distributions.size - 1].timeMills
        return CommonResponse(
            "분봉 데이터 조회에 성공하였습니다",
            "200",
            StockDistributionResponse(stockDistribution, lastViewTimeMills!!)
        )
    }
}