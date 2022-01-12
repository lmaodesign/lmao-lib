package design.lmao.lib.holograms

import design.lmao.lib.bukkit.entity.LmaoEntity
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.metadata.FixedMetadataValue

class HologramEntity(
    private val hologram: Hologram,
    private val index: Int,
) : LmaoEntity(EntityType.ARMOR_STAND)
{
    override fun spawnAtLocation(location: Location)
    {
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