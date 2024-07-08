package bakenosuke.serde.benchmark.moshi

import bakenosuke.serde.benchmark.SerdeProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.serializersModule
import kotlinx.serialization.serializer
import kotlin.reflect.typeOf

class MoshiSerdeProvider : SerdeProvider<Moshi> {

    private val moshi: Moshi by lazy { init() }

    override fun name() = "moshi"

    override fun init(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    override fun <V : Any> serialise(target: V): String {
        return moshi.adapter(target.javaClass).toJson(target)
    }

    override fun <V : Any> deserialise(json: String, clazz: Class<V>): V {
        return moshi.adapter(clazz).fromJson(json)!!
    }

}