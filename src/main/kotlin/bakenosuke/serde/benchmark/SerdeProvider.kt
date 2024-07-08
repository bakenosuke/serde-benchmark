package bakenosuke.serde.benchmark

interface SerdeProvider<T, Z> {

    fun name(): String

    fun init(): T

    fun <V : Any> serialise(target: V): Z

    fun <V : Any> deserialise(content: Z, clazz: Class<V>): V

}