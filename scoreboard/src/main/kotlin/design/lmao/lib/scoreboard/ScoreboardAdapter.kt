package design.lmao.lib.scoreboard

import org.bukkit.entity.Player

abstract class ScoreboardAdapter(
    val id: String,
    val weight: Int = 0
)
{
    abstract fun getElement(
        player: Player
    ): ScoreboardElement?

    fun element(
        builder: ScoreboardElement.() -> Unit
    ): ScoreboardElement
    {
        return ScoreboardElement().apply(builder)
    }
}