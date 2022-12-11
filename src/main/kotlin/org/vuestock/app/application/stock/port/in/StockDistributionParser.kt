package org.vuestock.app.application.stock.port.`in`

import org.vuestock.app.application.stock.port.`in`.dto.StockPrice

interface StockDistributionParser {

    fun parse(rawJsonBody: Map<*, *>): List<StockPrice>
}