package org.vuestock.app.infrastructure.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class CacheConfiguration {

    @Bean
    fun cacheManager(): CacheManager {
        val caches = VuestockCacheType.values().map {
            CaffeineCache(it.cacheName,
                          Caffeine.newBuilder().recordStats()
                                  .expireAfterWrite(it.expireAfterWrite.toLong(), TimeUnit.SECONDS)
                                  .maximumSize(it.maximumSize.toLong())
                                  .build())
        }
        val cacheManager = SimpleCacheManager()
        cacheManager.setCaches(caches)
        return cacheManager
    }
}