package org.vuestock.app.infrastructure.cache

enum class VuestockCacheType(
    val cacheName: String,
    val expireAfterWrite: Int,
    val maximumSize: Int
) {
    KIS_AUTHORIZATION(
        "kis_authorization",
        60,
        1
    )
}