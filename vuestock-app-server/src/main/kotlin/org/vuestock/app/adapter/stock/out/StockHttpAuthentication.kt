package org.vuestock.app.adapter.stock.out

interface StockHttpAuthentication {

    fun authenticate(appKey: String, appSecret: String): String
}