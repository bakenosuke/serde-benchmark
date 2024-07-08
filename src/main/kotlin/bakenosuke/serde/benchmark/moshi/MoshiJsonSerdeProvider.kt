package bakenosuke.serde.benchmark.moshi

import bakenosuke.serde.benchmark.SerdeProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MoshiJsonSerdeProvider : SerdeProvider<Moshi> {

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