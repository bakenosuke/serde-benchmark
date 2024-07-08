package bakenosuke.serde.benchmark.gson

import bakenosuke.serde.benchmark.SerdeProvider
import com.google.gson.Gson

class GsonJsonSerdeProvider : SerdeProvider<Gson, String> {

    private val gson: Gson by lazy { init() }

    override fun name() = "gson"

    override fun init(): Gson {
        return Gson()
    }

    override fun <V : Any> serialise(target: V): String {
        return gson.toJson(target)
    }

    override fun <V : Any> deserialise(content: String, clazz: Class<V>): V {
        return gson.fromJson(content, clazz)
    }

}