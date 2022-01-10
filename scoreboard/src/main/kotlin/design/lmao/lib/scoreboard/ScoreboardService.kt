package design.lmao.lib.scoreboard

import design.lmao.lib.scoreboard.updater.ScoreboardUpdaterHandler
import design.lmao.lib.scoreboard.updater.ScoreboardUpdaterHandlerThread
import design.lmao.lib.scoreboard.updater.impl.ObjectiveScoreboardUpdaterHandler
import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import java.util.logging.Logger

@Service
object ScoreboardService
{
    @Inject
    @JvmStatic
    lateinit var LOGGER: Logger

    var delay: Long = 20L

    var adapter: ScoreboardAdapter? = null
    var updater: ScoreboardUpdaterHandler = ObjectiveScoreboardUpdaterHandler

    @Configure
    fun configure()
    {
        ScoreboardUpdaterHandlerThread.run()
    }
}