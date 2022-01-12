package design.lmao.lib.scoreboard.updater

import com.google.common.util.concurrent.ThreadFactoryBuilder
import design.lmao.lib.scoreboard.ScoreboardAdapter
import gg.scala.flavor.inject.Inject
import gg.scala.flavor.inject.condition.Named
import gg.scala.flavor.service.Service
import org.bukkit.Bukkit
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.properties.Delegates

class ScoreboardUpdaterHandlerThread : Runnable
{
    @Inject
    lateinit var logger: Logger

    @delegate:Inject
    @delegate:Named("delay")
    var delay by Delegates.notNull<Long>()

    @Inject
    lateinit var updater: ScoreboardUpdaterHandler

    @Inject
    lateinit var adapters: List<ScoreboardAdapter>

    fun configure()
    {
        if (adapters.isEmpty())
        {
            logger.log(
                Level.SEVERE,
                "Adapters list is empty, if you're not using `lmao-lib` scoreboards, it is recommended to remove the module."
            )
            return
        }

        val executor = Executors.newSingleThreadScheduledExecutor(
            ThreadFactoryBuilder().setNameFormat("DBL Lib - Scoreboard Updater").build()
        )

        executor.scheduleAtFixedRate(this, 0L, delay * 50L, TimeUnit.MILLISECONDS)
    }

    override fun run()
    {
        Bukkit.getOnlinePlayers()
            .filter { it.isOnline && it != null }
            .forEach { player ->
                val lines = mutableListOf<String>()
                var title: String? = null

                adapters
                    .sortedBy { it.weight }
                    .forEach {
                        val element = it.getElement(player)

                        if (element == null)
                        {
                            this.logger.log(
                                Level.WARNING,
                                "adapter.getElement() returned null for [${player.uniqueId}] ${player.name}"
                            )
                            return
                        }

                        lines += element.lines

                        if (title == null)
                        {
                            title = element.title
                        }
                    }

                updater.displayElement(
                    player,
                    title ?: "Not set",
                    lines
                )
            }
    }
}
