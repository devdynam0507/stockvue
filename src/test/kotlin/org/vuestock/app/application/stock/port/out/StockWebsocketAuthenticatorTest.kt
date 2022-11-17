package org.vuestock.app.application.stock.port.out

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.vuestock.app.infrastructure.httpclient.HttpClientConfiguration

@SpringBootTest(classes = [StockWebsocketAuthenticator::class, HttpClientConfiguration::class])
internal class StockWebsocketAuthenticatorTest (
    val stockWebsocketAuthenticator: StockWebsocketAuthenticator,
    @Value("\${kis.appKey}")
    private var appKey: String,
    @Value("\${kis.appSecret}")
    private var appSecret: String
) : AnnotationSpec() {

    @Test
    fun `웹소켓 인증 키를 잘 받아오는지 테스트`() {
        val response = stockWebsocketAuthenticator.authenticate(appKey, appSecret)
        response.shouldNotBeNull()
    }
}