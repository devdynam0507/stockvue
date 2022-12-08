package org.vuestock.app.application.stock.port.out.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class RealTimeStockInfoSend(
    val header: KisWebsocketHeader,
    val body: KisWebsocketBody
)

data class KisWebsocketHeader(
    val approvalKey: String,
    @get:JsonProperty("custtype")
    val customerType: String,
    val trType: String,
    @get:JsonProperty("content-type")
    val contentType: String = "utf-8"
)

data class KisWebsocketInput(
    val trId: String,
    val trKey: String
)

data class KisWebsocketBody(
    val input: KisWebsocketInput
)