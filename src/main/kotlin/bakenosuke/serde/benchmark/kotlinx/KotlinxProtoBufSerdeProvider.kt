package bakenosuke.serde.benchmark.kotlinx

import bakenosuke.serde.benchmark.SerdeProvider
import com.google.gson.Gson
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.serializersModule
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer
import kotlin.reflect.typeOf

@OptIn(ExperimentalSerializationApi::class)
class KotlinxProtoBufSerdeProvider : SerdeProvider<ProtoBuf, ByteArray> {
    override fun name() = "kotlinx-protobuf"

    override fun init(): ProtoBuf {
        return ProtoBuf.Default
    }

    override fun <V : Any> serialise(target: V): ByteArray {
        return ProtoBuf.encodeToByteArray(serializersModule.serializer(target.javaClass), target)
    }

    override fun <V : Any> deserialise(content: ByteArray, clazz: Class<V>): V {
        return ProtoBuf.decodeFromByteArray(serializersModule.serializer(clazz), content) as V
    }

}