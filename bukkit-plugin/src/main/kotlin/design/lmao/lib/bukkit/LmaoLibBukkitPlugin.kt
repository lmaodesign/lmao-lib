package design.lmao.lib.bukkit

import design.lmao.lib.common.LmaoLibPlatform
import gg.scala.flavor.Flavor
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

class LmaoLibBukkitPlugin : JavaPlugin()
{
    private lateinit var platform: LmaoLibPlatform
    private lateinit var flavor: Flavor

    override fun onEnable()
    {
        this.platform = LmaoLibPlatform()
        this.flavor = Flavor.create<LmaoLibPlatform>()

        flavor.bind<JavaPlugin>() to this
        flavor.bind<PluginManager>() to this.server.pluginManager

        platform.start(
            this.logger, flavor
        )
    }

    override fun onDisable()
    {
        this.platform.close()
    }
}