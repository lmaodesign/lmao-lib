package design.lmao.lib.common

import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

object ClassReifiedParameterUtil
{
    fun Any.getTypes(): List<Class<*>>
    {
        return this::class.getTypes()
    }

    inline fun <reified T> Any.hasTypeOf(
        index: Int = -1
    ): Boolean
    {
        return this::class.hasTypeOf<T>(index)
    }

    fun KClass<*>.getTypes(): List<Class<*>>
    {
        return (this.java.genericSuperclass as ParameterizedType).actualTypeArguments
            .map {
                it as Class<*>
            }
            .toList()
    }

    inline fun <reified T> KClass<*>.hasTypeOf(
        index: Int = -1
    ): Boolean
    {
        return if (index == -1)
        {
            this.getTypes().any {
                it == T::class.java
            }
        } else
        {
            this.getTypes()[index] == T::class.java
        }
    }
}