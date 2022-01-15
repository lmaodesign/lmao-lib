package design.lmao.lib.mojang

import design.lmao.lib.cache.Cache.Companion.createCache
import design.lmao.lib.common.CatchWrapper.getOrNull
import java.util.*

object UUIDFetcher
{
    private val uuidCache = createCache<String, UUID>()

    fun nameToUUID(name: String): UUID?
    {
        return this.uuidCache.retrieve(name) ?: fetch(
            MOJANG_ENDPOINT,
            "users/profile/minecraft/$name"
        ) {
            return this.getOrNull {
                UUID.fromString(this.getString("").insertHyphens())
            }
        }
    }

    fun String.toMinecraftUUID(): UUID?
    {
        return nameToUUID(this)
    }

    private fun String.insertHyphens(): String
    {
        return StringBuffer(this).apply {
            listOf(
                20,
                16,
                12,
                8
            ).forEach {
                this.insert(it, '-')
            }
        }.toString()
    }
}