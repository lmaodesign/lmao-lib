package design.lmao.lib.spigot

import design.lmao.lib.common.LmaoLibPlatform
import gg.scala.flavor.Flavor
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

class LmaoLibSpigotPlatform : JavaPlugin()
{
    private lateinit var platform: LmaoLibPlatform
    private lateinit var flavor: Flavor

    companion object
    {
        @JvmStatic
        lateinit var INSTANCE: JavaPlugin
    }

    override fun onEnable()
    {
        INSTANCE = this

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
