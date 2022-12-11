package org.vuestock.app.application.authentication.service

import org.springframework.stereotype.Service
import org.vuestock.app.application.authentication.port.`in`.JwtTokenDecryptPort
import org.vuestock.app.infrastructure.security.JwtProvider

@Service
class JwtTokenDecryptStringClaimUseCase(
    val jwtProvider: JwtProvider
): JwtTokenDecryptPort<String> {

    override fun decryptToken(jwt: String): String {
        return jwtProvider.decrypt(jwt, "email", String::class.java)
    }
}