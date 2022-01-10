package design.lmao.lib.scoreboard.builder

import design.lmao.lib.scoreboard.ScoreboardElement

class ElementBuilder: ScoreboardElement()
{
    fun lines(
        patch: MutableList<String>.() -> Unit
    ): ElementBuilder
    {
        return this.apply {
            mutableListOf<String>()
                .apply(patch)
                .forEach {
                    this += it
                }
        }
    }

    fun title(
        patch: () -> String
    ): ElementBuilder
    {
        return this.apply {
            this.title = patch.invoke()
        }
    }
}