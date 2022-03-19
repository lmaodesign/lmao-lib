package design.lmao.lib.spigot.example

import design.lmao.lib.common.container.PluginContainer
import design.lmao.lib.common.container.PluginContainerValidator
import design.lmao.lib.common.container.Validate
import design.lmao.lib.common.lifecycle.Disable
import design.lmao.lib.common.lifecycle.Enable
import design.lmao.lib.spigot.plugin.LmaoLibPlugin
import gg.scala.flavor.inject.Inject
import org.bukkit.plugin.Plugin

/**
 * @author GrowlyX
 * @since 3/19/2022
 */
class ExamplePlugin : LmaoLibPlugin()

@PluginContainerValidator
object ExampleValidator
{
    @Validate
    fun validate(plugin: Plugin): Boolean
    {
        return plugin.name == "Example"
    }
}

@PluginContainer
object ExampleContainer
{
    @Inject
    lateinit var plugin: Plugin

    @Enable
    fun enable()
    {
        plugin.logger.info("Heyyy")
    }

    @Disable
    fun disable()
    {
        plugin.logger.info("Bye lomao")
    }
}
