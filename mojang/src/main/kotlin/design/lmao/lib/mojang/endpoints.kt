package design.lmao.lib.mojang

import kong.unirest.Unirest
import kong.unirest.json.JSONObject

const val MOJANG_ENDPOINT = "https://api.mojang.com/"
const val MOJANG_SESSION_ENDPOINT = "https://sessionserver.mojang.com"

fun fetch(
    endpoint: String,
    path: String
): JSONObject
{
    val response = Unirest
        .get("$endpoint/$path")
        .asJson()

    val body = response.body

    return body.`object`
}

inline fun <reified T> fetch(
    endpoint: String,
    path: String,
    action: JSONObject.() -> T
): T
{
    return action.invoke(
        fetch(
            endpoint, path
        )
    )
}