package design.lmao.lib.common

object CatchWrapper
{
    inline fun <reified T> Any.getOrNull(
        body: (Any) -> T
    ): T?
    {
        // could check with if (this.has(key)) instead of catching the exception,
        // but this allows us to return null not just if the field is not in the object.
        // it will also return null if any other exceptions occur.
        return try
        {
            body.invoke(this)
        } catch (ignored: Exception)
        {
            null
        }
    }
}