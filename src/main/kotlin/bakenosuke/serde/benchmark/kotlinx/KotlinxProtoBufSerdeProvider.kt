package bakenosuke.serde.benchmark.kotlinx

import bakenosuke.serde.benchmark.SerdeProvider
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json.Default.serializersModule
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer

@OptIn(ExperimentalSerializationApi::class)
class KotlinxProtoBufSerdeProvider : SerdeProvider<ProtoBuf, ByteArray> {
    override fun name() = "kotlinx-protobuf"

    override fun init(): ProtoBuf {
        return ProtoBuf.Default
    }

    override fun <V : Any> serialise(target: V): ByteArray {
        return ProtoBuf.encodeToByteArray(serializersModule.serializer(target.javaClass), target)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> deserialise(content: ByteArray, clazz: Class<V>): V {
        return ProtoBuf.decodeFromByteArray(serializersModule.serializer(clazz), content) as V
    }

}