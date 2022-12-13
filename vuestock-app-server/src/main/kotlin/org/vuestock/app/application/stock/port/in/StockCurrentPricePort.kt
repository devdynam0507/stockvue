package org.vuestock.app.application.stock.port.`in`

import org.vuestock.app.application.stock.port.`in`.dto.StockPrice

interface StockCurrentPricePort {

    fun getCurrentPrice(stockCode: String, offHourExclude: Boolean): StockPrice
}