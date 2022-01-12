package design.lmao.lib.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import gg.scala.flavor.Flavor
import java.util.logging.Logger

open class LmaoLibPlatform
{
    lateinit var flavor: Flavor

    private val builder = GsonBuilder()
        .setLongSerializationPolicy(LongSerializationPolicy.STRING)
        .setPrettyPrinting()

    open fun start(
        logger: Logger = Logger.getLogger(this::class.java.name),
        flavor: Flavor = Flavor.create<LmaoLibPlatform>()
    )
    {
        this.flavor = flavor

        this.flavor.bind<Flavor>() to flavor
        this.flavor.bind<LmaoLibPlatform>() to this
        this.flavor.bind<Logger>() to logger

        this.flavor.bind<GsonBuilder>() to builder
        this.flavor.bind<Gson>() to builder.create()

        this.flavor.startup()
    }

    open fun close()
    {
        this.flavor.close()
    }
}