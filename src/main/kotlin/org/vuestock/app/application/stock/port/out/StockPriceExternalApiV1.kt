package org.vuestock.app.application.stock.port.out

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.vuestock.app.adapter.stock.out.StockDistributionExternal
import org.vuestock.app.adapter.stock.out.command.KisApiHeader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class StockPriceExternalApiV1(
    private val restTemplate: RestTemplate,
    @Value("\${kis.http-endpoint}")
    private val endpoint: String,
    private val format: DateTimeFormatter? = DateTimeFormatter.ofPattern("HHmmss")
) : StockDistributionExternal {

    override fun requestDistributions(
        kisApiHeader: KisApiHeader,
        stockCode: String,
        benchmarkTime: LocalDateTime
    ): Map<*, *> {
        val headers = HttpHeaders()
        val time = format!!.format(benchmarkTime)
        val url = "$endpoint/uapi/domestic-stock/v1/quotations/inquire-time-itemchartprice?" +
                "FID_COND_MRKT_DIV_CODE=J&" +
                "FID_INPUT_ISCD=$stockCode&" +
                "FID_INPUT_HOUR_1=$time&" +
                "FID_PW_DATA_INCU_YN=N&" +
                "FID_ETC_CLS_CODE="
        headers["authorization"] = "Bearer ${kisApiHeader.accessToken}"
        headers["appkey"] = kisApiHeader.appKey
        headers["appsecret"] = kisApiHeader.appSecret
        headers["tr_id"] = kisApiHeader.trId
        val httpEntity = HttpEntity(null, headers)
        val emptyMap = emptyMap<Any, Any>()
        try {
            val response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Map::class.java)
            if (response.statusCode != HttpStatus.OK || response.body == null) {
                return emptyMap
            }
            return response.body.orEmpty()
        }
        catch (e: Exception) {
            return emptyMap
        }
    }
}