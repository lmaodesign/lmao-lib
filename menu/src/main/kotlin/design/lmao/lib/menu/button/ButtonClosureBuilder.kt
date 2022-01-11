package design.lmao.lib.menu.button

import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class ButtonClosureBuilder(
    material: Material,
    meta: ItemMeta?
) : Button(
    material, meta
)
{
    constructor(material: Material) : this(ItemStack(material))
    constructor(itemStack: ItemStack) : this(itemStack.type, itemStack.itemMeta)

    fun lore(
        patch: MutableList<String>.() -> Unit
    ): ButtonClosureBuilder
    {
        return this.apply {
            if (this.lore == null)
            {
                this.lore = mutableListOf()
            }

            patch.invoke(this.lore!!)
        }
    }

    fun displayName(
        patch: () -> String
    ): ButtonClosureBuilder
    {
        return this.apply {
            this.displayName = patch.invoke()
        }
    }

    fun data(
        patch: () -> Byte
    ): ButtonClosureBuilder
    {
        return this.apply {
            this.data = patch.invoke()
        }
    }

    fun action(
        patch: (InventoryClickEvent.() -> Unit)
    ): ButtonClosureBuilder
    {
        return this.apply {
            this.action = patch
        }
    }
}