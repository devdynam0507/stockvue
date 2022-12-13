package org.vuestock.app.application.stock.port.`in`

import org.vuestock.app.application.stock.port.`in`.dto.StockPrice

interface StockCurrentPriceParser {

    fun parse(rawJsonBody: Map<*, *>): StockPrice
}