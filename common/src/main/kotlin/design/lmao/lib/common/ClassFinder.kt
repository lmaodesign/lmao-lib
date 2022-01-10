package design.lmao.lib.common

import java.util.*

object ClassFinder
{
    fun getLoadedClasses(): Vector<Class<*>>
    {
        val classLoader = Thread.currentThread().contextClassLoader
        val classesField = ClassLoader::class.java.getDeclaredField("classes")
            .apply {
                this.isAccessible = true
            }

        return (classesField.get(classLoader) as Vector<Class<*>>).clone() as Vector<Class<*>>
    }

    inline fun <reified T> typeOfClass(
        vararg exclude: Class<*>
    ): Class<T>?
    {
        return this.getLoadedClasses()
            .find {
                !exclude.contains(it) && T::class.java.isAssignableFrom(it) && it != T::class.java
            } as Class<T>?
    }

    fun classesInPackage(
        name: String
    ): List<Class<*>>
    {
        return this.getLoadedClasses()
            .filter {
                it.`package`.equals(name)
            }
    }
}