package design.lmao.lib.scoreboard.updater.impl

import design.lmao.lib.scoreboard.updater.ScoreboardUpdaterHandler
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

object ObjectiveScoreboardUpdaterHandler : ScoreboardUpdaterHandler
{
    private val identifiers = hashMapOf<Player, MutableList<String>>()

    override fun displayElement(
        player: Player,
        title: String,
        lines: List<String>
    )
    {
        this.identifiers.putIfAbsent(player, mutableListOf())

        val identifiers = this.identifiers[player]!!

        val board = this.retrieveScoreboard(player)
        val objective = this.retrieveObjective(board)

        for (index in 0..16)
        {
            val identifier = "${ChatColor.values()[index]}${ChatColor.WHITE}"

            if (lines.size - 1 < index)
            {
                if (objective.getScore(identifier) == null)
                {
                    break
                }

                this.removeEntry(player, identifier)
            }
            else
            {
                val line = lines[index]

                val team = this.retrieveTeam(board, identifier)
                val text = this.splitText(line)

                team.apply {
                    this.prefix = text.first
                    this.suffix = text.second
                }

                identifiers += identifier
                objective.getScore(identifier).score = -index
            }
        }

        objective.displayName = title
        player.scoreboard = board
    }

    override fun removeEntry(player: Player, identifier: String)
    {
        this.retrieveScoreboard(player).apply {
            resetScores(identifier)
        }
    }

    override fun removeElements(player: Player)
    {
        val scoreboard = this.retrieveScoreboard(player)

        this.identifiers[player]?.forEach {
            scoreboard.resetScores(it)
        }
    }

    private fun retrieveScoreboard(
        player: Player
    ): Scoreboard
    {
        return if (player.scoreboard == Bukkit.getScoreboardManager().mainScoreboard)
        {
            Bukkit.getScoreboardManager().newScoreboard
        } else
        {
            player.scoreboard
        }
    }

    private fun retrieveObjective(
        scoreboard: Scoreboard
    ): Objective
    {
        return scoreboard.getObjective("boardHandler") ?: scoreboard.registerNewObjective("boardHandler", "dummy")
            .apply {
                this.displaySlot = DisplaySlot.SIDEBAR
            }
    }

    private fun retrieveTeam(
        scoreboard: Scoreboard,
        identifier: String
    ): Team
    {
        var team = scoreboard.getTeam(identifier)

        if (team == null)
        {
            team = scoreboard.registerNewTeam(identifier)
        }

        if (!team.entries.contains(identifier))
        {
            team.addEntry(identifier)
        }

        return team
    }

    private fun splitText(
        text: String
    ): Pair<String, String>
    {
        return if (text.length < 17)
        {
            Pair(text, "")
        }
        else
        {
            val left = text.substring(0, 16)
            val right = text.substring(16)

            return if (left.endsWith("${ChatColor.COLOR_CHAR}"))
            {
                Pair(
                    left.substring(0, left.length - 1),
                    StringUtils.left("${ChatColor.getLastColors(left)}${ChatColor.COLOR_CHAR}$right", 16)
                )
            }
            else
            {
                Pair(
                    left,
                    StringUtils.left("${ChatColor.getLastColors(left)}$right", 16)
                )
            }
        }
    }
}