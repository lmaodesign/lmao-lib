package design.lmao.lib.menu

import design.lmao.lib.menu.button.Button
import design.lmao.lib.menu.button.ButtonClosureBuilder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class Menu(
    val menuType: MenuType = MenuType.INVENTORY,
    val player: Player,

    val size: Int,
    val title: String
)
{
    private var inventory: Inventory? = null
    open val buttons = hashMapOf<Int, Button>()

    abstract fun tick()

    fun button(
        material: Material,
        builder: ButtonClosureBuilder.() -> Unit
    ): Button
    {
        return ButtonClosureBuilder(material).apply(builder)
    }

    fun button(
        itemStack: ItemStack,
        builder: ButtonClosureBuilder.() -> Unit
    ): Button
    {
        return ButtonClosureBuilder(itemStack).apply(builder)
    }

    open fun update()
    {
        val inventory = this.inventory ?: this.menuType.createInventory(this)

        this.getButtonsInRange().forEach {
            inventory.setItem(
                it.key, it.value.createItem()
            )
        }

        if (inventory != this.inventory)
        {
            player.apply {
                this.closeInventory(); this.openInventory(inventory)
            }
        } else
        {
            player.updateInventory()
        }

        this.inventory = inventory

        MenuService.menus[player] = this
    }

    open fun getButtonsInRange(): Map<Int, Button>
    {
        return this.buttons
    }

    open fun click(
        event: InventoryClickEvent
    )
    {
        this.getButtonsInRange()[event.slot]?.action?.invoke(event)
    }

    open fun close(
        event: InventoryCloseEvent
    )
    {
        MenuService.menus.remove(this.player)
    }
}