package org.vuestock.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class VueStockConfigServerApp

fun main(args: Array<String>) {
    runApplication<VueStockConfigServerApp>(*args)
}