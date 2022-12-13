package org.vuestock.app.application.authentication.port.`in`

interface JwtTokenDecryptPort<T> {

    /**
     * 다양한 형태의 JWT 토큰의 클레임을 가져올 수 있는 메소드입니다.
     * 이 메소드에서는 만료된 토큰일 경우 던지는 {@exception JWTVerificationException}을 처리하지 않습니다.
     * */
    fun decryptToken(jwt: String): T
}