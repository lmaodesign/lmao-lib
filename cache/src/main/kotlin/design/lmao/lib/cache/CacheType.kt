package design.lmao.lib.common.cache

import java.util.concurrent.CompletableFuture

enum class CacheType
{
    REDIS_CACHE
    {
        override fun <K, V> cache(
            key: K,
            value: V
        ): Cache<K, V>.() -> Unit
        {
            return {
                this.redisCache.storeAsync(key, value)
            }
        }

        override fun <K, V> retrieveAsync(key: K): Cache<K, V>.() -> CompletableFuture<V?>
        {
            return {
                this.redisCache.retrieveAsync(key)
            }
        }
    },
    LOCAL_MEM_CACHE
    {
        override fun <K, V> cache(
            key: K,
            value: V
        ): Cache<K, V>.() -> Unit
        {
            return {
                this.localCache[key] = value
            }
        }

        override fun <K, V> retrieveAsync(key: K): Cache<K, V>.() -> CompletableFuture<V?>
        {
            return {
                CompletableFuture.supplyAsync {
                    this.localCache[key]
                }
            }
        }
    };

    abstract fun <K, V> cache(key: K, value: V): Cache<K, V>.() -> Unit
    abstract fun <K, V> retrieveAsync(key: K): Cache<K, V>.() -> CompletableFuture<V?>

    fun <K, V> retrieve(key: K): Cache<K, V>.() -> V?
    {
        return {
            this@CacheType.retrieveAsync<K, V>(key).invoke(this).join()
        }
    }
}