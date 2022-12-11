package org.vuestock.app.infrastructure.common

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StockUtils {
    companion object {
        fun isOffHours(date: LocalDateTime): Boolean {
            return (date.hour < 9 || date.hour > 15) || DateUtils.isWeekend(date)
        }
    }
}