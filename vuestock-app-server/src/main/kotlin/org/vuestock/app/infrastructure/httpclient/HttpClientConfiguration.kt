package org.vuestock.app.infrastructure.httpclient

import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import javax.net.ssl.SSLContext

@Configuration
class HttpClientConfiguration {

    @Bean
    fun requestFactory(): ClientHttpRequestFactory {
        val httpClient: CloseableHttpClient = HttpClients.custom()
            .setSSLContext(SSLContext.getDefault())
            .build()
        val factory = HttpComponentsClientHttpRequestFactory()
        factory.httpClient = httpClient
        factory.setConnectTimeout(3000)
        factory.setReadTimeout(3000)
        return factory
    }

    @Bean
    fun restTemplate(factory: ClientHttpRequestFactory): RestTemplate? {
        return RestTemplate(factory)
    }
}