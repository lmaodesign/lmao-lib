import design.lmao.lib.menu.Menu
import design.lmao.lib.menu.button.Button
import org.bukkit.Material
import org.bukkit.entity.Player

class ButtonClosureBuilderTest(
    player: Player
) : Menu(
    player = player,
    title = "hey",
    size = 9
)
{
    override fun tick()
    {
        this.buttons[0] = button(Material.DIAMOND_SWORD) {
            this.lore {
                this += "&7&m${"-".repeat(20)}"
                this += "Diamonddd Sword...."
                this += "&7&m${"-".repeat(20)}"
            }

            this.displayName {
                "Diamond Sowd."
            }
        }

        this.buttons[1] = Button(Material.DIAMOND_SWORD).apply {
            this.lore = mutableListOf(
                "&7&m${"-".repeat(20)}",
                "Diamonddd Sword....",
                "&7&m${"-".repeat(20)}"
            )

            this.displayName = "Diamond Sowd."
        }
    }
}