package design.lmao.lib.holograms

import design.lmao.lib.common.StringUtil
import org.bukkit.ChatColor
import org.bukkit.Location

class Hologram(
    val id: String = StringUtil.generateRandomString(8),
    var location: Location
)
{
    val lines = mutableListOf<String>()

    fun translateLines(): List<String>
    {
        return lines.map {
            ChatColor.translateAlternateColorCodes('&', it)
        }
    }
}