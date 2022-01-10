package design.lmao.lib.scoreboard.builder

import design.lmao.lib.scoreboard.ScoreboardAdapter
import design.lmao.lib.scoreboard.ScoreboardElement
import design.lmao.lib.scoreboard.ScoreboardService
import org.bukkit.entity.Player
import java.util.logging.Level

class ScoreboardAdapterBuilder(
    id: String,
    weight: Int = 0
) : ScoreboardAdapter(
    id,
    weight
)
{
    private var linesPatch: (Player.() -> MutableList<String>)? = null
    private var titlePatch: (Player.() -> String)? = null

    override fun getElement(player: Player): ScoreboardElement?
    {
        if (this.titlePatch == null || this.linesPatch == null)
        {
            ScoreboardService.LOGGER.log(
                Level.SEVERE,
                "either titlePatch or linesPatch is null, consult your plugin developer."
            )
            return null
        }

        return element {
            this.title = titlePatch!!.invoke(player)
            this.lines = linesPatch!!.invoke(player)
        }
    }

    fun lines(
        patch: (Player) -> MutableList<String>
    ): ScoreboardAdapterBuilder
    {
        return this.apply {
            this.linesPatch = patch
        }
    }

    fun title(
        patch: (Player) -> String
    ): ScoreboardAdapterBuilder
    {
        return this.apply {
            this.titlePatch = patch
        }
    }
}