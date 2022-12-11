package org.vuestock.app.application.stock.port.`in`.dto

data class StockSearchResultsWithPrice(
    val stockName: String,
    val stockCode: String,
    val stockPrice: StockPrice
)