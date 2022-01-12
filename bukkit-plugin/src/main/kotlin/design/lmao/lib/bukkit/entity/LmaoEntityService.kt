package design.lmao.lib.bukkit.entity

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import com.google.gson.stream.JsonReader
import design.lmao.lib.common.serializer.AbstractTypeSerializer
import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Close
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileReader

@Service
object LmaoEntityService
{
    @Inject
    lateinit var plugin: JavaPlugin

    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LmaoEntity::class.java, AbstractTypeSerializer<LmaoEntity>())
        .setLongSerializationPolicy(LongSerializationPolicy.STRING)
        .setPrettyPrinting()
        .create()

    val entities = mutableListOf<LmaoEntity>()
    val dataFolder = File(plugin.dataFolder, "/entities/")

    @Configure
    fun configure()
    {
        this.loadPersistentEntities()
    }

    @Close
    fun close()
    {
        this.storePersistentEntities()
    }

    fun storePersistentEntities()
    {
        if (!this.dataFolder.exists())
        {
            this.dataFolder.mkdirs()
        }

        this.entities
            .filter { it.persistent }
            .forEach {
                LmaoEntityFile(it).writeEntity()
            }
    }

    fun loadPersistentEntities()
    {
        if (!this.dataFolder.exists())
        {
            this.dataFolder.mkdirs()
        }

        if (this.dataFolder.isDirectory)
        {
            dataFolder.listFiles()?.forEach {
                val reader = FileReader(it)
                val jsonReader = JsonReader(reader)

                val data = gson.fromJson<LmaoEntity>(jsonReader, LmaoEntity::class.java)

                this.entities += data
            }
        }
    }
}