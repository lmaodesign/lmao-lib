import design.lmao.lib.common.ClassReifiedParameterUtil.hasTypeOf
import org.junit.jupiter.api.Test

class ReifiedClassUtilTest
{
    @Test
    fun test()
    {
        val lol = Lol()

        println(lol.hasTypeOf<String>())
        println(Lol::class.hasTypeOf<String>())
        println(Lol::class.hasTypeOf<Int>())
    }
}

abstract class Example<T>

class Lol : Example<String>()
