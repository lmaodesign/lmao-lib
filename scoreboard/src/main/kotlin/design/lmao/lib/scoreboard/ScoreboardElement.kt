package design.lmao.lib.scoreboard

import org.bukkit.ChatColor

open class ScoreboardElement(
    var title: String = "Not set"
)
{
    var lines = mutableListOf<String>()

    operator fun plusAssign(
        value: String
    )
    {
        this.lines += value
    }

    infix fun add(
        value: String
    )
    {
        this.lines += ChatColor.translateAlternateColorCodes('&', value)
    }

    infix fun addAll(
        value: List<String>
    )
    {
        this.lines.addAll(
            value.map { 
                ChatColor.translateAlternateColorCodes('&', it)
            }
        )
    }
    
    fun addAll(
        vararg value: String
    )
    {
        this.lines.addAll(
            value.map {
                ChatColor.translateAlternateColorCodes('&', it)
            }
        )
    }

    fun add(
        index: Int,
        value: String
    )
    {
        this.lines.add(index, ChatColor.translateAlternateColorCodes('&', value))
    }
}
