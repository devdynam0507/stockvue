package org.vuestock.app.application.authentication.service

import org.springframework.stereotype.Service
import org.vuestock.app.application.authentication.port.`in`.CheckLoggedInPort
import org.vuestock.app.application.authentication.port.`in`.JwtTokenDecryptPort

@Service
class CheckLoggedInUseCase(
    private val stringClaimTokenProvider: JwtTokenDecryptPort<String>
) : CheckLoggedInPort {

    override fun isLoggedIn(jwt: String): Boolean {
        return try {
            stringClaimTokenProvider.decryptToken(jwt)
            true
        } catch (e: Exception) {
            false
        }
    }
}