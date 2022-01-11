package design.lmao.lib.holograms

import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.metadata.FixedMetadataValue

class HologramEntity(
    val hologram: Hologram,
    val index: Int,
    val parent: HologramEntity?
)
{
    private val children = linkedSetOf<HologramEntity>()

    fun spawn()
    {
        val location = hologram.location.add(0.0, 0.4 * index, 0.0)
        val entity = location.world.spawnEntity(location, EntityType.ARMOR_STAND) as ArmorStand

        entity.setMetadata(
            "hologram", FixedMetadataValue(
                HologramService.plugin, hologram.id
            )
        )

        entity.setGravity(false)

        entity.canPickupItems = false
        entity.customName = hologram.translateLines()[index]

        entity.isCustomNameVisible = true
        entity.isVisible = false
    }
}