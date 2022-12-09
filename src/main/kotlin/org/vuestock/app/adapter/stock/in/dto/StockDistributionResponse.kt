package org.vuestock.app.adapter.stock.`in`.dto

import org.vuestock.app.application.stock.port.`in`.dto.StockDistribution

data class StockDistributionResponse(
    val stockDistribution: StockDistribution,
    val lastViewTimeMills: Long
)
