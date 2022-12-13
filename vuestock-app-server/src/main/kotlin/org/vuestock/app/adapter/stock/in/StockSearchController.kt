package org.vuestock.app.adapter.stock.`in`

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.vuestock.app.application.stock.port.`in`.StockSearchPort
import org.vuestock.app.application.stock.port.`in`.dto.StockSearchResultsWithPrice

@RestController
@RequestMapping("/api/v1/stock/search")
class StockSearchController(
    val stockSearchPort: StockSearchPort
) {

    @GetMapping("/multiple")
    fun stockSearchByStockName(@RequestParam("stockName") stockName: String): List<StockSearchResultsWithPrice> {
        return stockSearchPort.searchByStockNameWithPrices(stockName)
    }
}