package design.lmao.lib.menu

import design.lmao.lib.menu.button.Button
import design.lmao.lib.menu.button.ButtonClosureBuilder
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

abstract class Menu(
    private val menuType: MenuType = MenuType.INVENTORY,
    private val player: Player,

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

    fun update()
    {
        val inventory = this.inventory ?: this.menuType.createInventory(this)

        this.buttons.forEach {
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

    fun click(
        event: InventoryClickEvent
    )
    {
        this.buttons[event.slot]?.action?.invoke(event)
    }
}