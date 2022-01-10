package design.lmao.lib.scoreboard

import org.bukkit.entity.Player

interface ScoreboardAdapter
{
    fun getElement(
        player: Player
    ): ScoreboardElement?

    fun element(
        builder: ScoreboardElement.() -> Unit
    ): ScoreboardElement
    {
        return ScoreboardElement().apply(builder)
    }
}