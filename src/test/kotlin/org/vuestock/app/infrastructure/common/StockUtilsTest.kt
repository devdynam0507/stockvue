package org.vuestock.app.infrastructure.common

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

import java.time.LocalDateTime

internal class StockUtilsTest : AnnotationSpec() {

    @Test
    fun `평일 장외시간 일 경우`() {
        var dateTime = LocalDateTime.now()
        dateTime = dateTime.withMonth(12).withDayOfMonth(9).withHour(17)
        StockUtils.isOffHours(dateTime) shouldBe true

        dateTime = dateTime.withMonth(12).withDayOfMonth(9).withHour(8)
        StockUtils.isOffHours(dateTime) shouldBe true
    }

    @Test
    fun `휴일인 경우`() {
        var now = LocalDateTime.now()
        now = now.withMonth(12).withDayOfMonth(10)

        StockUtils.isOffHours(now) shouldBe true
    }
}