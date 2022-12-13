package org.vuestock.app.adapter.member.`in`.dto

import org.vuestock.app.application.stock.port.`in`.dto.StockPrice

data class MemberStockPinResponse(
    val stockCode: String,
    val stockName: String,
    val stockPrice: StockPrice
)
