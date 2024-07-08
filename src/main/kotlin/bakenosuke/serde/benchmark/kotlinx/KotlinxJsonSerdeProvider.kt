package bakenosuke.serde.benchmark.kotlinx

import bakenosuke.serde.benchmark.SerdeProvider
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.serializersModule
import kotlinx.serialization.serializer

class KotlinxJsonSerdeProvider : SerdeProvider<Json> {

    override fun name() = "kotlinx"

    override fun init(): Json {
        return Json.Default
    }

    override fun <V : Any> serialise(target: V): String {
        return Json.encodeToString(serializersModule.serializer(target.javaClass), target)
    }

    override fun <V : Any> deserialise(json: String, clazz: Class<V>): V {
        return Json.decodeFromString(serializersModule.serializer(clazz), json) as V
    }

}