package org.vuestock.app.application.stock.service

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate
import org.vuestock.app.application.stock.port.`in`.StockCurrentPricePort
import org.vuestock.app.application.stock.port.out.StockHttpAuthenticator
import org.vuestock.app.application.stock.port.out.StockPriceExternalApiV1

@SpringBootTest( classes = [
    RestTemplate::class,
    StockAuthorizationResolverImpl::class,
    StockHttpAuthenticator::class,
    StockCurrentCurrentPriceUseCase::class,
    StockPriceExternalApiV1::class,
])
internal class StockCurrentPriceUseCaseTest(
    val stockCurrentPricePort: StockCurrentPricePort,
) : AnnotationSpec() {

    @Test
    fun `현재 삼성전자 주가 정보를 잘 얻어오는지 테스트`() {
        val stock = stockCurrentPricePort.getCurrentPrice("005930", false)
        stock.currentPrice shouldBeGreaterThan 0
    }
}