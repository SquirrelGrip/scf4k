package com.github.squirrelgrip.scf4k

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import java.util.concurrent.TimeUnit

abstract class Cache<K, V> {
    val cache: LoadingCache<K, V> by lazy {
        CacheBuilder.newBuilder().recordStats().expireAfterWrite(8, TimeUnit.HOURS).build(object: CacheLoader<K, V>() {
            override fun load(key: K): V {
                return key.loadValue()
            }

        })
    }

    abstract fun K.loadValue() : V

    open fun invalidateCache() {
        cache.invalidateAll()
    }
}