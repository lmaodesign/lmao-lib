package design.lmao.lib.common

object StringUtil
{
    private val ALPHANUMERIC_CHAR_SET = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun generateRandomString(
        length: Int,
        characterSet: List<Char> = ALPHANUMERIC_CHAR_SET
    ): String
    {
        return (1..length)
            .map { characterSet.random() }
            .joinToString("")
    }
}