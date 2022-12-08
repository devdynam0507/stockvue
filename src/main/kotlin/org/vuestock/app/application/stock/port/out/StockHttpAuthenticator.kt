package org.vuestock.app.application.stock.port.out

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.vuestock.app.adapter.stock.out.StockHttpAuthentication
import org.vuestock.app.application.stock.port.out.dto.KisHttpAccessTokenRequest
import org.vuestock.app.application.stock.port.out.dto.KisHttpAccessTokenResponse
import org.vuestock.app.infrastructure.cache.VuestockCacheType

@Service
class StockHttpAuthenticator(
    private val restTemplate: RestTemplate,
    @Value("\${kis.http-oauth-endpoint}")
    private val oauth2Endpoint: String
) : StockHttpAuthentication {

    @Cacheable(cacheNames = ["kis_authorization"], key = "access-token")
    override fun authenticate(appKey: String, appSecret: String): String {
        val accessTokenRequest = KisHttpAccessTokenRequest(
            appKey = appKey,
            appSecret =  appSecret
        )
        try {
            val response = restTemplate.postForEntity(
                oauth2Endpoint,
                accessTokenRequest,
                KisHttpAccessTokenResponse::class.java
            )
            if (response.statusCode != HttpStatus.OK) {
                return ""
            }
            return response.body!!.accessToken
        }
        catch (e: RestClientException) {
            return ""
        }
    }
}