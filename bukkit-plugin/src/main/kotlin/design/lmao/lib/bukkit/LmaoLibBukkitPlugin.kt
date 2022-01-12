package design.lmao.lib.bukkit

import design.lmao.lib.cache.cache.Cache.Companion.createCache
import design.lmao.lib.common.LmaoLibPlatform
import gg.scala.flavor.Flavor
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class LmaoLibBukkitPlugin : JavaPlugin()
{
    private lateinit var platform: LmaoLibPlatform
    private lateinit var flavor: Flavor

    companion object
    {
        lateinit var INSTANCE: JavaPlugin
    }

    override fun onEnable()
    {
        INSTANCE = this

        this.platform = LmaoLibPlatform()
        this.flavor = Flavor.create<LmaoLibPlatform>()

        flavor.bind<JavaPlugin>() to this
        flavor.bind<PluginManager>() to this.server.pluginManager

        val cache = createCache<String, UUID>()

        platform.start(
            this.logger, flavor
        )
    }

    override fun onDisable()
    {
        this.platform.close()
    }
}