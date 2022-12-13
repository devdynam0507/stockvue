package org.vuestock.common

import java.time.DayOfWeek
import java.time.LocalDateTime

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