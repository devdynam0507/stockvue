package org.vuestock.app.application.stock.port.`in`

import org.vuestock.app.application.stock.port.`in`.dto.StockDistribution
import java.time.LocalDateTime

interface StockDistributionPort {

    fun getDistribution(stockCode: String, date: LocalDateTime): StockDistribution
}