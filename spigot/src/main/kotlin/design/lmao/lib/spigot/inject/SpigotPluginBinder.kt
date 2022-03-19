package design.lmao.lib.spigot.inject

import design.lmao.lib.common.inject.PluginBinder
import gg.scala.flavor.Flavor
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.messaging.Messenger
import org.bukkit.scheduler.BukkitScheduler
import java.util.logging.Logger

/**
 * Bind commonly used spigot
 * services to a [Plugin].
 *
 * @author GrowlyX
 * @since 3/19/2022
 */
object SpigotPluginBinder : PluginBinder<Plugin>
{
    override fun bind(c: Plugin): Flavor.() -> Unit = {
        this.bind<Logger>() to Bukkit.getLogger()
        this.bind<Server>() to Bukkit.getServer()
        this.bind<Messenger>() to Bukkit.getMessenger()
        this.bind<BukkitScheduler>() to Bukkit.getScheduler()
        this.bind<PluginManager>() to Bukkit.getPluginManager()

        listOf(
            this.bind<Plugin>(),
            this.bind<JavaPlugin>()
        ).forEach {
            it to c
        }
    }
}
