package design.lmao.lib.scoreboard.updater

import org.bukkit.entity.Player

interface ScoreboardUpdaterHandler
{
    fun displayElement(
        player: Player,
        title: String,
        lines: List<String>
    )

    fun removeEntry(
        player: Player,
        identifier: String
    )

    fun removeElements(
        player: Player
    )
}