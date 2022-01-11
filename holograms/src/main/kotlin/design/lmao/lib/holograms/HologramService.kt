package design.lmao.lib.holograms

import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Service
import org.bukkit.plugin.java.JavaPlugin

@Service
object HologramService
{
    @Inject
    lateinit var plugin: JavaPlugin
}