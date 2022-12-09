package org.vuestock.app.application.stock.port.`in`.dto

import org.vuestock.app.application.stock.type.StockSign

data class StockPrice(
    // 전일 대비 가격 차이
    val previousDayPriceDiff: Int,
    // 전일 대비 부호
    val previousDaySign: StockSign,
    // 전일 대비 가격 차이 퍼센트
    val previousDayPriceDiffPercent: Float,
    // 현재 주식 가격
    val currentPrice: Int,
    // 시간
    val time: String?,
    // 날짜
    val date: String?,
    // 시간 + 날짜의 time mills
    val timeMills: Long?
) {
    companion object {
        fun empty(): StockPrice {
            return StockPrice(
                0,
                StockSign.None,
                0.0f,
                0,
                "",
                "",
                0L
            )
        }
    }
}