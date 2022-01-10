package design.lmao.lib.scoreboard.updater

import design.lmao.lib.scoreboard.ScoreboardService
import gg.scala.flavor.inject.Inject
import org.bukkit.Bukkit
import java.util.logging.Level
import java.util.logging.Logger

object ScoreboardUpdaterHandlerThread : Thread()
{
    @Inject
    lateinit var logger: Logger

    private val delay = ScoreboardService.delay
    private val updater = ScoreboardService.updater
    private val adapter = ScoreboardService.adapter

    override fun run()
    {
        if (adapter == null)
        {
            this.logger.log(
                Level.WARNING, "adapter value was not set"
            )
            return
        }

        while (true)
        {
            Bukkit.getOnlinePlayers().forEach {
                if (it.isOnline && it != null)
                {
                    val element = adapter.getElement(it)

                    if (element == null)
                    {
                        this.logger.log(
                            Level.WARNING, "adapter.getElement() returned null for ${it.uniqueId.toString()}"
                        )
                        return
                    }

                    updater.displayElement(
                        it, element
                    )
                }
            }

            sleep(delay * 50)
        }
    }
}