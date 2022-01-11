package design.lmao.lib.holograms.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent

object HologramInteractListener : Listener
{
    @EventHandler
    fun onInteract(
        event: PlayerInteractEntityEvent
    )
    {
        if (event.rightClicked.hasMetadata("hologram"))
        {
            event.isCancelled = true
        }
    }
}