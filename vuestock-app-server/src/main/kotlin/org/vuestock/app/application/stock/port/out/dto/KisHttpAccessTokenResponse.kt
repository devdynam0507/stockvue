package org.vuestock.app.application.stock.port.out.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KisHttpAccessTokenResponse(
    @get:JsonProperty("access_token")
    val accessToken: String = "",
    @get:JsonProperty("token_type")
    val tokenType: String = "",
    @get:JsonProperty("expires_in")
    val expiresIn: Int = 0
)
