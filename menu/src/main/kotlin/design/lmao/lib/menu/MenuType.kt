package design.lmao.lib.menu

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory

enum class MenuType
{
    INVENTORY
    {
        override fun createInventory(menu: Menu): Inventory
        {
            return Bukkit.createInventory(
                null, menu.size, menu.title
            )
        }
    };

    abstract fun createInventory(menu: Menu): Inventory
}