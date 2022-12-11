package org.vuestock.app.infrastructure.common

import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDateTime

@Service
class DateUtils {
    companion object {
        fun isWeekend(localDateTime: LocalDateTime): Boolean {
            val dayOfWeek = localDateTime.dayOfWeek
            return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY
        }

        fun getRecentWeekdayFromWeekend(localDateTime: LocalDateTime): LocalDateTime {
            if (!isWeekend(localDateTime)) {
                return localDateTime
            }
            val dayOfWeek = localDateTime.dayOfWeek
            return if(dayOfWeek == DayOfWeek.SATURDAY) {
                localDateTime.minusDays(1)
            } else {
                localDateTime.minusDays(2)
            }
        }
    }
}