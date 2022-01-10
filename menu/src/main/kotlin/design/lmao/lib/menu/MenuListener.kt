package design.lmao.lib.menu

import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

object MenuListener : Listener
{
    @Override
    fun click(
        event: InventoryClickEvent
    )
    {
        val player = event.whoClicked as Player
        val menu = MenuService.retrieveMenu(player)

        menu?.click(event)
    }
}