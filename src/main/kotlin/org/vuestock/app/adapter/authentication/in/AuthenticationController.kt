package org.vuestock.app.adapter.authentication.`in`

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.vuestock.app.application.authentication.port.`in`.CheckLoggedInPort

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(
    private val checkLoggedInPort: CheckLoggedInPort
) {

    @PostMapping
    fun isLoggedIn(@CookieValue(value = "authenticationToken", defaultValue = "token") jwt: String):
            ResponseEntity<Boolean> {
        val isLoggedIn = checkLoggedInPort.isLoggedIn(jwt)
        if (!isLoggedIn) {
            return ResponseEntity.status(403).body(false)
        }
        return ResponseEntity.ok(true)
    }
}