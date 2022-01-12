package design.lmao.lib.scoreboard.updater

import design.lmao.lib.scoreboard.ScoreboardAdapter
import gg.scala.flavor.inject.Inject
import gg.scala.flavor.inject.condition.Named
import org.bukkit.Bukkit
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.properties.Delegates

class ScoreboardUpdaterHandlerThread : Thread()
{
    @Inject
    lateinit var logger: Logger

    @Inject
    @delegate:Named("delay")
    var delay by Delegates.notNull<Long>()

    @Inject
    lateinit var updater: ScoreboardUpdaterHandler

    @Inject
    lateinit var adapters: List<ScoreboardAdapter>

    override fun run()
    {
        if (adapters.isEmpty())
        {
            logger.log(
                Level.SEVERE,
                "Adapters list is empty, if you're not using `lmao-lib` scoreboards, it is recommended to remove the module."
            )
            return
        }

        while (true)
        {
            try
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
            } catch (exception: Exception)
            {
                exception.printStackTrace()
            }

            try
            {
                sleep(delay * 50)
            } catch (exception: Exception)
            {
                logger.severe("An exception was thrown while trying to iterate the scoreboard updater thread, you should probably reboot the server & notify a developer. (${exception.message})")
            }
        }
    }
}
