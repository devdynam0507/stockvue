package org.vuestock.app.adapter.stock.out

interface StockWebsocketAuthentication {

    fun authenticate(appKey: String, appSecret: String): String?
}