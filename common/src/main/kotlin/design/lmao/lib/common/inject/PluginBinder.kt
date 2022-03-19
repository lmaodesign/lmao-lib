package design.lmao.lib.common.inject

import gg.scala.flavor.Flavor

/**
 * Attach default binders to a flavor
 * instance within context [C].
 *
 * @author GrowlyX
 * @since 3/19/2022
 */
interface PluginBinder<C>
{
    fun bind(c: C): Flavor.() -> Unit
}
