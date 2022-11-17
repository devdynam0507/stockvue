package org.vuestock.app.application.authentication.service

import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.stereotype.Service
import org.vuestock.app.application.authentication.port.`in`.CheckLoggedInPort
import org.vuestock.app.infrastructure.security.JwtProvider

@Service
class CheckLoggedInUseCase(
    private val jwtProvider: JwtProvider
) : CheckLoggedInPort {

    override fun isLoggedIn(jwt: String): Boolean {
        return try {
            jwtProvider.decrypt(jwt, "email", String::class.java)
            true
        } catch (e: JWTVerificationException) {
            false
        }
    }
}