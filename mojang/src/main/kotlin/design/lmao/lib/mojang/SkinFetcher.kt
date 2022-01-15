package design.lmao.lib.mojang

import design.lmao.lib.cache.Cache.Companion.createCache
import design.lmao.lib.common.CatchWrapper.getOrNull
import kong.unirest.Unirest
import java.util.*

object SkinFetcher
{
    private val skinCache = createCache<UUID, Skin>()

    fun uuidToSkin(uniqueId: UUID): Skin?
    {
        return this.skinCache.retrieve(uniqueId) ?: fetch(
            MOJANG_SESSION_ENDPOINT,
            "session/minecraft/profile/$uniqueId"
        ) {
            this.getOrNull {
                val properties = this.getJSONObject("properties")

                return@getOrNull Skin(
                    properties.getString("value"),
                    properties.getString("signature")
                )
            }
        }
    }
}

data class Skin(
    val value: String,
    val signature: String
)
