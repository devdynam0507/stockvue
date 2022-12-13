package org.vuestock.common

import java.time.LocalDateTime

class StockUtils {
    companion object {
        fun isOffHours(date: LocalDateTime): Boolean {
            return (date.hour < 9 || date.hour > 15) || DateUtils.isWeekend(date)
        }
    }
}