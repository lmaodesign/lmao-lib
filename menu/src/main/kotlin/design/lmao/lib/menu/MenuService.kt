package design.lmao.lib.menu

import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import org.bukkit.entity.Player
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

@Service
object MenuService
{
    @Inject
    lateinit var plugin: JavaPlugin

    @Inject
    lateinit var pluginManager: PluginManager

    val menus = hashMapOf<Player, Menu>()

    @Configure
    fun configure()
    {
        this.pluginManager.registerEvents(
            MenuListener, plugin
        )
    }

    fun retrieveMenu(
        player: Player
    ): Menu?
    {
        return this.menus[player]
    }
}