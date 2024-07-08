package bakenosuke.serde.benchmark

interface SerdeProvider<T> {

    fun name(): String

    fun init(): T

    fun <V : Any> serialise(target: V): String

    fun <V : Any> deserialise(json: String, clazz: Class<V>): V

}