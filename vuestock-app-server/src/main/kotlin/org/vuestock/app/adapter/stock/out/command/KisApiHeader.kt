package org.vuestock.app.adapter.stock.out.command

import org.vuestock.app.application.stock.port.`in`.dto.StockAuthorization

data class KisApiHeader(
    val accessToken: String,
    val appKey: String,
    val appSecret: String,
    val trId: String
) {
    companion object {
        fun from(stockAuthorization: StockAuthorization, trId: String): KisApiHeader {
            return KisApiHeader(
                accessToken = stockAuthorization.accessToken,
                appKey = stockAuthorization.appKey,
                appSecret = stockAuthorization.appSecret,
                trId = trId
            )
        }
    }
}
