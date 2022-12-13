package org.vuestock.app.application.stock.service

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@EnableAutoConfiguration(exclude = [
    BatchAutoConfiguration::class
])
internal class StockSearchUseCaseTest(
    val stockSearchUseCase: StockSearchUseCase
) : AnnotationSpec() {

    @Test
    fun `"삼성"이 포함된 주식 정보를 모두 얻어오는지 확인`() {
        val searchByStockNameWithPrices = stockSearchUseCase.searchByStockNameWithPrices("삼성")

        searchByStockNameWithPrices.size shouldBeGreaterThan 0
    }
}