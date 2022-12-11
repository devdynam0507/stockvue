package org.vuestock.app.application.stock.service

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.ints.shouldBeGreaterThan
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate
import org.vuestock.app.application.stock.port.`in`.StockDistributionPort
import org.vuestock.app.application.stock.port.out.StockHttpAuthenticator
import org.vuestock.app.application.stock.port.out.StockPriceExternalApiV1
import java.time.LocalDateTime

@SpringBootTest(classes = [
    StockPriceExternalApiV1::class,
    StockAuthorizationResolverImpl::class,
    StockCurrentCurrentPriceUseCase::class,
    RestTemplate::class,
    StockHttpAuthenticator::class,
    StockDistributionUseCase::class
])
internal class StockDistributionUseCaseTest(
    private val stockDistributionPort: StockDistributionUseCase
): AnnotationSpec() {

    @Test
    fun `삼성전자 모든 분봉 데이터가 잘 얻어와지는지 테스트`() {
        val stockDistribution = stockDistributionPort.getDistribution("005930", LocalDateTime.now())
        stockDistribution.distributions.size shouldBeGreaterThan 0
    }
}