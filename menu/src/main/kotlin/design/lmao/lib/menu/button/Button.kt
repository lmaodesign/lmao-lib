package design.lmao.lib.menu.button

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

open class Button(
    private val material: Material,
    private val meta: ItemMeta?
) : Cloneable
{
    var action: ((InventoryClickEvent) -> Unit)? = null

    var displayName: String? = null
    var lore: MutableList<String>? = null

    var amount: Int? = null
    var data: Byte? = null

    constructor(material: Material) : this(ItemStack(material))
    constructor(itemStack: ItemStack) : this(itemStack.type, itemStack.itemMeta)

    fun createItem(): ItemStack
    {
        val item = ItemStack(this.material)

        if (this.amount != null)
        {
            item.amount = this.amount!!
        }

        if (this.data != null)
        {
            item.durability = this.data!!.toShort()
        }

        val meta: ItemMeta? = this.meta ?: item.itemMeta

        if (meta != null)
        {
            if (this.displayName != null)
            {
                meta.displayName = ChatColor.translateAlternateColorCodes('&', displayName)
            }

            if (this.lore != null)
            {
                meta.lore = this.lore!!
                    .map {
                        ChatColor.translateAlternateColorCodes('&', it)
                    }
            }
        }

        return item.also {
            it.itemMeta = meta
        }
    }
}