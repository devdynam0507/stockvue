package org.vuestock.app.application.stock.port.out

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate

@SpringBootTest(classes = [StockHttpAuthenticator::class, RestTemplate::class])
internal class StockHttpAuthenticatorTest(
    private val stockHttpAuthenticator: StockHttpAuthenticator,
    @Value("\${kis.appKey}")
    private val appKey: String,
    @Value("\${kis.appSecret}")
    private val appSecret: String
) : AnnotationSpec() {

    @Test
    fun `인증키가 잘 받아와지는지 테스트`() {
        val key = stockHttpAuthenticator.authenticate(appKey, appSecret)
        key shouldNotBe ""
    }
}