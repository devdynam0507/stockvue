package org.vuestock.app.application.stock.exceptions

class StockNotFoundException(
    val stockCode: String,
    val stockName: String,
    override val message: String
) : Exception()