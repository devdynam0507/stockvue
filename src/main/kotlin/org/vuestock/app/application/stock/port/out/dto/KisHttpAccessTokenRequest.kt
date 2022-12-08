package org.vuestock.app.application.stock.port.out.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KisHttpAccessTokenRequest(
    @get:JsonProperty("grant_type")
    val grantType: String = "client_credentials",
    @get:JsonProperty("appkey")
    val appKey: String,
    @get:JsonProperty("appsecret")
    val appSecret: String
)
