import design.lmao.lib.scoreboard.ScoreboardAdapter
import design.lmao.lib.scoreboard.ScoreboardElement
import design.lmao.lib.scoreboard.builder.ElementBuilder
import design.lmao.lib.scoreboard.builder.ScoreboardAdapterBuilder
import org.bukkit.entity.Player

class ScoreboardAdapterTest : ScoreboardAdapter("adapter-1")
{
    override fun getElement(player: Player): ScoreboardElement
    {
        return element {
            this += "Hey"

            if (player.location.x >= 5)
            {
                this += "bye ur stupid"
            }

            this.title = "test scoreboard"
        }
    }
}

class ScoreboardAdapterTest2 : ScoreboardAdapter("adapter-2")
{
    override fun getElement(player: Player) = ScoreboardElement("test scoreboard")
        .apply {
            this += "hey"

            if (player.location.x >= 5)
            {
                this += "bye ur stupid"
            }
        }
}

class ScoreboardAdapter3 : ScoreboardAdapter("adapter-3")
{
    override fun getElement(player: Player): ScoreboardElement
    {
        return ElementBuilder()
            .title { player.location.toString() }
            .lines {
                this += "hey"

                if (player.location.x >= 5)
                {
                    this += "bye ur stupid"
                }
            }
    }
}

class ScoreboardAdapter4
{
    init
    {
        ScoreboardAdapterBuilder("adapter-4")
            .lines { player ->
                return@lines mutableListOf<String>().apply {
                    this += player.name
                }
            }
            .title {
                it.uniqueId.toString()
            }
    }
}