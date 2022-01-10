package design.lmao.lib.scoreboard.updater

import design.lmao.lib.scoreboard.ScoreboardElement
import org.bukkit.entity.Player

interface ScoreboardUpdaterHandler
{
    fun displayElement(
        player: Player,
        element: ScoreboardElement
    )

    fun removeEntry(
        player: Player,
        identifier: String
    )

    fun removeElements(
        player: Player
    )
}