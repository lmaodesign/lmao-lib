package design.lmao.lib.spigot.plugin

import design.lmao.lib.common.container.PluginContainer
import design.lmao.lib.common.container.PluginContainerValidator
import design.lmao.lib.common.container.Validate
import design.lmao.lib.common.lifecycle.Disable
import design.lmao.lib.common.lifecycle.Enable
import design.lmao.lib.common.lifecycle.PostEnable
import design.lmao.lib.common.lifecycle.Restart
import design.lmao.lib.common.lifecycle.state.LifecycleState
import design.lmao.lib.spigot.inject.SpigotPluginBinder
import gg.scala.flavor.Flavor
import gg.scala.flavor.FlavorOptions
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.lang.reflect.Method
import java.util.logging.Level

/**
 * @author GrowlyX
 * @since 3/19/2022
 */
open class LmaoLibPlugin : JavaPlugin()
{
    internal var state =
        LifecycleState.INITIALIZING

    internal val flavor by lazy {
        Flavor.create(
            this::class,
            FlavorOptions(logger)
        )
    }

    internal lateinit var container: Any

    override fun onLoad()
    {
        // TODO: 3/19/2022 load dependencies
    }

    override fun onEnable()
    {
        val validators = flavor
            .findSingletons<PluginContainerValidator>()

        if (validators.isNotEmpty())
        {
            val validation = validators
                .map { Pair(it, it.annotated<Validate>()) }
                .filter { it.second != null && it.second!!.returnType == Boolean::class.java }

            for (method in validation)
            {
                val result = method.second!!
                    .invoke(method.first, this) as Boolean

                if (!result)
                {
                    shutdownContainer("Failed a plugin validation check! Look above for more information.")
                    return
                }
            }
        }

        val initialization =
            System.currentTimeMillis()

        val container = flavor
            .findSingletons<PluginContainer>()
            .firstOrNull()

        if (container == null)
        {
            logger.info("[LmaoLib] No PluginContainer was found!")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        this.container = container

        // attaching default bindings
        SpigotPluginBinder
            .bind(this)
            .invoke(flavor)

        logger.info("Injecting container...")
        flavor.inject(container)
        logger.info("Injected container.")

        val enable = container
            .annotated<Enable>()
            ?: return

        performContainerInvocation(
            container, enable
        ) {
            shutdownContainer("An exception occurred while starting up container... (${it.message})")
        }

        // Flavor catches any exceptions thrown during
        // the process of service injection and/or startup.
        flavor.startup()

        val postEnable = container
            .annotated<PostEnable>()

        if (postEnable != null)
        {
            logger.info("Performing post enable tasks...")

            performContainerInvocation(
                container, enable
            ) {
                logger.log(Level.WARNING, it) {
                    "Failed to perform post enable tasks"
                }
            }

            logger.info("Performed post enable tasks!")
        }

        logger.info("Finished initialization in ${
            System.currentTimeMillis() - initialization
        }ms.")

        state = LifecycleState.IDLE
    }

    override fun onDisable()
    {
        state = LifecycleState.DESTROYING

        logger.info("Destroying services...")
        flavor.close()
        logger.info("Destroyed services.")

        val enable = container
            .annotated<Disable>()
            ?: return

        performContainerInvocation(
            container, enable
        ) {
            logger.log(Level.WARNING, it) {
                "An exception occurred while disabling container"
            }
        }
    }

    private fun shutdownContainer(message: String)
    {
        logger.info("[Container] $message")
        Bukkit.getPluginManager().disablePlugin(this)
    }

    private fun performContainerInvocation(
        container: Any, method: Method,
        failure: (Exception) -> Unit
    )
    {
        try
        {
            if (method.parameterCount == 1)
            {
                method.invoke(container)
            } else
            {
                method.invoke(container, this)
            }
        } catch (exception: Exception)
        {
            failure.invoke(exception)
        }
    }

    private inline fun <reified T : Annotation> Any.annotated(): Method?
    {
        return this.javaClass.methods
            .firstOrNull { it.getAnnotation(T::class.java) != null }
    }
}
