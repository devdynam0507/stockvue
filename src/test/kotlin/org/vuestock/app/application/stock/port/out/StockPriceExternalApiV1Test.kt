package org.vuestock.app.application.stock.port.out

import io.kotest.core.spec.style.AnnotationSpec
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.client.RestTemplate
import org.vuestock.app.adapter.stock.out.StockHttpAuthentication
import org.vuestock.app.adapter.stock.out.command.KisApiHeader
import java.time.LocalDateTime

@SpringBootTest(classes = [
    RestTemplate::class,
    StockPriceExternalApiV1::class,
    StockHttpAuthenticator::class
])
internal class StockPriceExternalApiV1Test(
    private val stockPriceExternalApiV1: StockPriceExternalApiV1,
    private val stockAuthentication: StockHttpAuthentication,
    @Value("\${kis.appKey}")
    private val appKey: String,
    @Value("\${kis.appSecret}")
    private val appSecret: String
) : AnnotationSpec() {

    @Test
    fun `KIS Api 에서 현재 주식 가격 정보 얻어오기`() {
        val accessToken = stockAuthentication.authenticate(appKey, appSecret)
        val kisHeader = KisApiHeader(
            appKey = appKey,
            appSecret = appSecret,
            accessToken = accessToken,
            trId = "FHKST03010200"
        )
        // 삼성전자의 현재 주식 가격을 얻어온다
        stockPriceExternalApiV1.requestDistributions(kisHeader, "005930", LocalDateTime.now())
    }
}