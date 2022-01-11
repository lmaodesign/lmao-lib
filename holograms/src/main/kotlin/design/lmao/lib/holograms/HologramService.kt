package design.lmao.lib.holograms

import design.lmao.lib.holograms.listener.HologramInteractListener
import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

@Service
object HologramService
{
    @Inject
    lateinit var plugin: JavaPlugin

    @Inject
    lateinit var pluginManager: PluginManager

    @Configure
    fun configure()
    {
        this.pluginManager.registerEvents(
            HologramInteractListener, plugin
        )
    }
}