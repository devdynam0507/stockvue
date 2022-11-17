package org.vuestock.app.infrastructure.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Claim
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct


@Service
class JwtProvider {

    private lateinit var algorithm: Algorithm
    private lateinit var jwtVerifier: JWTVerifier

    @PostConstruct
    fun init() {
        algorithm = Algorithm.HMAC256("secret")
        jwtVerifier = JWT.require(algorithm).build()
    }

    fun <T> encrypt(claimKey: String, value: T, expiredSecond: Long): String {
        require(!(value is Map<*, *> || value is List<*>)) { "지원되지 않는 claim type 입니다." }
        return JWT.create()
            .withClaim(claimKey, value.toString())
            .withExpiresAt(getExpiredDate(expiredSecond))
            .sign(algorithm)
    }

    fun <T> encryptWithoutAlgorithm(
        claimKey: String, value: T, expiredSecond: Long
    ): String? {
        require(!(value is List<*> || value is Map<*, *>)) { "지원되지 않는 claim type 입니다." }
        return JWT.create()
            .withClaim(claimKey, value.toString())
            .withExpiresAt(getExpiredDate(expiredSecond))
            .sign(Algorithm.none())
    }

    fun <V> encryptMapWithoutAlgorithm(
        claims: Map<String, V>, expiredSecond: Long
    ): String? {
        val builder = JWT.create()
        claims.entries.forEach {
            builder.withClaim(it.key, it.value.toString())
        }
        return builder.withExpiresAt(getExpiredDate(expiredSecond))
            .sign(Algorithm.none())
    }

    fun <T> decrypt(token: String, claimKey: String, type: Class<out T>): T {
        require(
            !(MutableCollection::class.java.isAssignableFrom(type) || MutableMap::class.java.isAssignableFrom(
                type
            ))
        ) { "지원되지 않는 claim type 입니다." }
        return jwtVerifier.verify(token)
            .getClaim(claimKey)
            .`as`(type) as T
    }

    fun decryptWithNoAlgorithm(token: String): Map<String, Claim?> {
        return JWT.decode(token).claims
    }

    private fun getExpiredDate(expiredSecond: Long): Date {
        val now = Date()
        val expired = Date()
        expired.time = now.time + expiredSecond
        return expired
    }
}