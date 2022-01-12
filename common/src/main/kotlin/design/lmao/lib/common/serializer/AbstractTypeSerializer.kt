package design.lmao.lib.common.serializer

import com.google.gson.*
import design.lmao.lib.common.ClassReifiedParameterUtil.getTypes
import java.lang.reflect.Type

class AbstractTypeSerializer<T> : JsonSerializer<T>, JsonDeserializer<T>
{
    override fun serialize(src: T, typeOfSrc: Type, context: JsonSerializationContext): JsonElement
    {
        val type = this.getTypes()[0]

        return JsonObject().apply {
            this.addProperty("type", type.name)
            this.add("properties", context.serialize(src, typeOfSrc))
        }
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): T
    {
        val type = json.asJsonObject.get("type").asString
        val properties = json.asJsonObject.get("properties")

        return context.deserialize(properties, Class.forName(type))
    }
}
