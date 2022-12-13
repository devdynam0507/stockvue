package org.vuestock.app.application.stock.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.vuestock.app.adapter.stock.out.StockHttpAuthentication
import org.vuestock.app.application.stock.port.`in`.StockAuthorizationResolver
import org.vuestock.app.application.stock.port.`in`.dto.StockAuthorization

@Service
class StockAuthorizationResolverImpl(
    @Value("\${kis.appKey}")
    private val appKey: String,
    @Value("\${kis.appSecret}")
    private val appSecret: String,
    private val stockHttpAuthentication: StockHttpAuthentication
) : StockAuthorizationResolver {

    override fun getAuthorizationInfo(): StockAuthorization? {
        val accessToken = stockHttpAuthentication.authenticate(appKey, appSecret)
        return StockAuthorization(
            appKey = appKey,
            appSecret = appSecret,
            accessToken = accessToken
        )
    }
}