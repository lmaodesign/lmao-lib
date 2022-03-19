package design.lmao.lib.spigot.entity

import design.lmao.lib.spigot.LmaoLibSpigotPlatform
import design.lmao.lib.common.StringUtil
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.metadata.FixedMetadataValue
import java.lang.IllegalStateException

open class LmaoEntity(
    val entityType: EntityType,
    val id: String = StringUtil.generateRandomString(8),
    val persistent: Boolean = true,
    var location: Location? = null
)
{
    @Transient
    var bukkitEntity: Entity? = null

    val metadata = hashMapOf<String, Any>()

    @JvmOverloads
    constructor(
        entity: Entity,
        location: Location? = null
    ) : this(entity.type, location = location)
    {
        this.bukkitEntity = entity
    }

    init
    {
        if (this.bukkitEntity == null && this.location != null)
        {
            this.spawnAtLocation(this.location!!)
        }

        this.synchronizeBukkitMetadata()
    }

    open fun spawnAtLocation(location: Location)
    {
        this.location = location.apply {
            bukkitEntity = this.world.spawnEntity(this, entityType)
        }
    }

    fun addMetadata(
        key: String,
        value: Any
    )
    {
        if (this.bukkitEntity == null)
        {
            throw IllegalStateException("bukkitEntity is null.")
        }

        this.metadata[key] = value
        this.bukkitEntity!!.setMetadata(
            key, FixedMetadataValue(
                LmaoLibSpigotPlatform.INSTANCE, value
            )
        )
    }

    fun hasMetadata(key: String): Boolean
    {
        if (this.bukkitEntity == null)
        {
            throw IllegalStateException("bukkitEntity is null.")
        }

        return this.metadata.containsKey(key) || this.bukkitEntity!!.hasMetadata(key)
    }

    fun synchronizeBukkitMetadata()
    {
        if (this.bukkitEntity == null)
        {
            throw IllegalStateException("bukkitEntity is null.")
        }

        this.metadata.forEach {
            if (bukkitEntity!!.getMetadata(it.key).isEmpty())
            {
                bukkitEntity!!.setMetadata(
                    it.key, FixedMetadataValue(LmaoEntityService.plugin, it.value)
                )
            }
        }
    }
}
