package org.vuestock.app.application.stock.service

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

internal class StockDistributionDateResolverTest(
    private val stockDistributionDateResolver: StockDistributionDateResolver = StockDistributionDateResolver()
) : AnnotationSpec() {

    @Test
    fun `16시 이후 데이터가 제대로 보정이 되는지 테스트`() {
        val now = LocalDateTime.now()
        val date = LocalDateTime.of(
            now.year,
            now.month,
            now.dayOfMonth,
            19,
            0,
            0
        )
        val revised = stockDistributionDateResolver.revise(date)

        revised.hour shouldBeExactly 16
        revised.minute shouldBeExactly 0
        revised.second shouldBeExactly 0
        revised.year shouldBeExactly now.year
        revised.month shouldBe now.month
        revised.dayOfMonth shouldBeExactly now.dayOfMonth
    }

    @Test
    fun `오전 9시 이전 데이터가 제대로 보정이 되는지 테스트`() {
        val now = LocalDateTime.now()
        val date = LocalDateTime.of(
            now.year,
            now.month,
            now.dayOfMonth,
            5,
            0,
            0
        )
        val yesterday = now.minusDays(1)
        val revised = stockDistributionDateResolver.revise(date)
        revised.hour shouldBeExactly 16
        revised.minute shouldBeExactly 0
        revised.second shouldBeExactly 0
        revised.year shouldBeExactly yesterday.year
        revised.month shouldBe yesterday.month
        revised.dayOfMonth shouldBeExactly yesterday.dayOfMonth
    }

    @Test
    fun `오전 9시에서 오후 16시 이전 시간이 들어오면 시간 그대로 뱉어내는지 테스트`() {
        val now = LocalDateTime.now()
        val date = LocalDateTime.of(
            now.year,
            now.month,
            now.dayOfMonth,
            12,
            0,
            0
        )
        val revised = stockDistributionDateResolver.revise(date)
        revised.hour shouldBeExactly 12
        revised.minute shouldBeExactly 0
        revised.second shouldBeExactly 0
        revised.year shouldBeExactly now.year
        revised.month shouldBe now.month
        revised.dayOfMonth shouldBeExactly now.dayOfMonth
    }
}