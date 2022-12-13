package org.vuestock.app.application.stock.port.`in`

import org.vuestock.app.application.stock.port.`in`.dto.StockSearchResultsWithPrice

interface StockSearchPort {

    fun searchByStockNameWithPrices(stockName: String): List<StockSearchResultsWithPrice>

    fun searchByStockNameWithPriceOne(stockName: String): StockSearchResultsWithPrice
}