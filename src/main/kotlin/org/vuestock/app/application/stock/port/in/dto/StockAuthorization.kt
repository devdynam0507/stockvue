package org.vuestock.app.application.stock.port.`in`.dto

data class StockAuthorization(
    val appKey: String,
    val appSecret: String,
    val accessToken: String
)
