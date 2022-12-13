package org.vuestock.app.adapter.authentication.`in`

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.vuestock.app.application.authentication.port.`in`.CheckLoggedInPort

// TODO: @CookieValue 의 경우 나중 JWT Filter 가 적용됐을 때 @AuthenticationPrincipal 어노테이션을 이용하여 Resolve 한다.
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