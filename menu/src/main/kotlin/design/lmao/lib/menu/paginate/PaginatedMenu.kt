package design.lmao.lib.menu.paginate

import design.lmao.lib.menu.Menu
import design.lmao.lib.menu.MenuType
import design.lmao.lib.menu.button.Button
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.math.max

abstract class PaginatedMenu(
    menuType: MenuType = MenuType.INVENTORY,
    player: Player,

    size: Int,
    title: String
) : Menu(
    menuType, player, size, title
)
{
    var page = 1

    private val navigationButtons: Map<Int, Button>
        get()
        {
            return hashMapOf(
                0 to button(this.previousPageBuilder.invoke()) {
                    this.action {
                        this@PaginatedMenu.apply {
                            this.page = max(1, this.page - 1); this.update()
                        }
                    }
                },
                8 to button(this.nextPageBuilder.invoke()) {
                    this.action {
                        this@PaginatedMenu.apply {
                            this.page += 1; this.update()
                        }
                    }
                }
            )
        }

    var previousPageBuilder: (() -> ItemStack) = {
        ItemStack(Material.CARPET, 1, DyeColor.GRAY.data.toShort()).apply {
            val meta = this.itemMeta

            if (meta != null)
            {
                meta.displayName = "${ChatColor.GREEN}Previous Page"
            }

            this.itemMeta = meta
        }
    }

    var nextPageBuilder: (() -> ItemStack) = {
        ItemStack(Material.CARPET, 1, DyeColor.GRAY.data.toShort()).apply {
            val meta = this.itemMeta

            if (meta != null)
            {
                meta.displayName = "${ChatColor.GREEN}Next Page"
            }

            this.itemMeta = meta
        }
    }

    override fun getButtonsInRange(): Map<Int, Button>
    {
        val maxElements = this.size - 9

        val start = ((page - 1) * maxElements)
        val end = (start + maxElements) - 1

        val buttons = hashMapOf<Int, Button>()

        this.buttons.forEach {
            if (it.key in start..end)
            {
                buttons[it.key - ((maxElements) * (page - 1)) + 9] = it.value
            }
        }

        this.navigationButtons.forEach {
            buttons[it.key] = it.value
        }

        return buttons
    }
}