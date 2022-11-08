package org.vuestock.app

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = ["org.vuestock.app"])
class VuestockApp

fun main(args: Array<String>) {
    SpringApplication.run(VuestockApp::class.java, *args)
}