package design.lmao.lib.cache

import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.DataStoreType
import io.github.nosequel.data.store.StoreType
import java.util.concurrent.CompletableFuture

open class Cache<K, V>(
    private val cacheTypes: MutableList<CacheType> = mutableListOf(
        CacheType.LOCAL_MEM_CACHE,
        CacheType.REDIS_CACHE
    )
)
{
    companion object
    {
        inline fun <reified K, reified V> createCache(
            cacheTypes: MutableList<CacheType> = mutableListOf(
                CacheType.LOCAL_MEM_CACHE,
                CacheType.REDIS_CACHE
            )
        ): Cache<K, V>
        {
            return Cache<K, V>(
                cacheTypes
            ).apply {
                this.createRedisCache<K, V>()
            }
        }
    }

    val localCache = hashMapOf<K, V>()
    lateinit var redisCache: StoreType<K, V>

    inline fun <reified Key : K, reified Value : V> createRedisCache()
    {
        this.redisCache = DataHandler
            .createStoreType<Key, Value>(DataStoreType.REDIS) as StoreType<K, V>
    }

    fun store(key: K, value: V)
    {
        this.cacheTypes.forEach {
            it.cache(key, value)
        }
    }

    fun retrieve(key: K): V?
    {
        this.cacheTypes.forEach {
            val data = it.retrieve<K, V>(key).invoke(this@Cache)

            if (data != null)
            {
                return data
            }
        }

        return null
    }

    fun retrieveAsync(key: K): CompletableFuture<V?>
    {
        return CompletableFuture.supplyAsync {
            this.retrieve(key)
        }
    }
}