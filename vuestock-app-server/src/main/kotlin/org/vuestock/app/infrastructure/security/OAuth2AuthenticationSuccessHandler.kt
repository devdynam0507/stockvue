package org.vuestock.app.infrastructure.security

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Service
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class OAuth2AuthenticationSuccessHandler(
    private val jwtProvider: JwtProvider
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val oauth2User: OAuth2User = authentication!!.principal as OAuth2User
        val email: String = oauth2User.attributes!!["email"] as String
        val jwt = jwtProvider.encrypt("email", email, 1000 * 60 * 60 * 10)
        val tokenCookie = Cookie("authenticationToken", jwt)
        tokenCookie.path = "/"
        tokenCookie.domain = ".d261ex3d85zwvh.amplifyapp.com"
        tokenCookie.isHttpOnly = true
        tokenCookie.maxAge = 60 * 60 * 24

        response!!.addCookie(tokenCookie)
        response.sendRedirect("http://localhost:5050")
    }
}