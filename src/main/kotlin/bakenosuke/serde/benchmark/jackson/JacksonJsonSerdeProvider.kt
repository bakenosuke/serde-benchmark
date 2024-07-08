package bakenosuke.serde.benchmark.jackson

import bakenosuke.serde.benchmark.SerdeProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

class JacksonJsonSerdeProvider : SerdeProvider<ObjectMapper, String> {

    private val objectMapper: ObjectMapper by lazy { init() }

    override fun name() = "jackson"

    override fun init(): ObjectMapper {
        return ObjectMapper().registerKotlinModule()
    }

    override fun <V : Any> serialise(target: V): String {
        return objectMapper.writeValueAsString(target)
    }

    override fun <V : Any> deserialise(content: String, clazz: Class<V>): V {
        return objectMapper.readValue(content, clazz)
    }

}