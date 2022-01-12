package design.lmao.lib.bukkit.entity

import java.io.File
import java.io.FileWriter

class LmaoEntityFile(
    private val entity: LmaoEntity,
)
{
    private val file = File(LmaoEntityService.dataFolder, "${entity.id}-entity.json")

    fun writeEntity()
    {
        if (!file.exists())
        {
            this.file.createNewFile()
        }

        val writer = FileWriter(this.file)
        val data = LmaoEntityService.gson.toJson(entity)

        writer.write(data)
        writer.close()
    }
}