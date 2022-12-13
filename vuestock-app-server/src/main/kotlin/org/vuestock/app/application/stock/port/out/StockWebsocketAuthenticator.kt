package org.vuestock.app.application.stock.port.out

import net.minidev.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.vuestock.app.adapter.stock.out.StockWebsocketAuthentication
import org.vuestock.app.application.stock.port.out.dto.WebsocketApprovalResponse

@Service
class StockWebsocketAuthenticator @Autowired constructor(
    @Value("\${kis.websocket-auth-endpoint}")
    private val webSocketAuthenticatorEndpoint: String,
    private val httpClient: RestTemplate,
) : StockWebsocketAuthentication {

    override fun authenticate(appKey: String, appSecret: String): String? {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val json = JSONObject()
        json["appkey"] = appKey
        json["secretkey"] = appSecret
        val response =
            httpClient.postForEntity(webSocketAuthenticatorEndpoint, json, WebsocketApprovalResponse::class.java)
        if (response.statusCode != HttpStatus.OK || response.body == null) {
            return ""
        }
        return response.body!!.approval_key
    }
}