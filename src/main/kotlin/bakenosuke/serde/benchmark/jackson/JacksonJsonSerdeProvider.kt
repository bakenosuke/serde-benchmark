package bakenosuke.serde.benchmark.jackson

import bakenosuke.serde.benchmark.SerdeProvider
import com.fasterxml.jackson.databind.ObjectMapper

class JacksonJsonSerdeProvider : SerdeProvider<ObjectMapper> {

    private val objectMapper: ObjectMapper by lazy { init() }

    override fun name() = "jackson"

    override fun init(): ObjectMapper {
        return ObjectMapper()
    }

    override fun <V : Any> serialise(target: V): String {
        return objectMapper.writeValueAsString(target)
    }

    override fun <V : Any> deserialise(json: String, clazz: Class<V>): V {
        return objectMapper.readValue(json, clazz)
    }

}