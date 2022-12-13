package org.vuestock.app

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@ComponentScan(basePackages = ["org.vuestock"])
@SpringBootApplication
@EntityScan(basePackages = ["org.vuestock"])
@EnableJpaRepositories(basePackages = ["org.vuestock"])
@EnableJpaAuditing
class VuestockApp

fun main(args: Array<String>) {
    SpringApplication.run(VuestockApp::class.java, *args)
}