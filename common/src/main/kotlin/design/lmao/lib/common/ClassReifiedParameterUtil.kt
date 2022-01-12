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
        return this.java.getTypes()
    }

    inline fun <reified T> KClass<*>.hasTypeOf(
        index: Int = -1
    ): Boolean
    {
        return this.java.hasTypeOf<T>(index)
    }

    @JvmStatic
    fun Class<*>.getTypes(): List<Class<*>>
    {
        return (this.genericSuperclass as ParameterizedType).actualTypeArguments
            .map {
                it as Class<*>
            }
            .toList()
    }

    inline fun <reified T> Class<*>.hasTypeOf(
        index: Int = -1
    ): Boolean
    {
        return this.hasTypeOf(T::class.java, index)
    }

    @JvmStatic
    @JvmOverloads
    fun <T> Class<*>.hasTypeOf(
        type: Class<T>,
        index: Int = -1,
    ): Boolean
    {
        return if (index == -1)
        {
            this.getTypes().any {
                it == type.javaClass
            }
        } else
        {
            this.getTypes()[index] == type.javaClass
        }
    }
}