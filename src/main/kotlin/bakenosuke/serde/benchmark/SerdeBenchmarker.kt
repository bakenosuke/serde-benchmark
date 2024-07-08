package bakenosuke.serde.benchmark

import kotlin.time.measureTimedValue

class SerdeBenchmarker {
    fun init(
        serdeProvider: SerdeProvider<*, *>,
        iterations: Int = 1_000_000,
    ): BenchmarkResult {
        val results = mutableListOf<Long>()
        repeat(iterations) {
            val (_, duration) = measureTimedValue { serdeProvider.init() }
            results += duration.inWholeNanoseconds
        }
        return BenchmarkResult(
            iterations = iterations,
            durationNanos = results
        )
    }

    fun serialise(
        serdeProvider: SerdeProvider<*, *>,
        iterations: Int = 1_000_000,
        modelProvider: () -> Any,
    ): BenchmarkResult {
        val results = mutableListOf<Long>()
        repeat(iterations) {
            val model = modelProvider.invoke()
            val (_, duration) = measureTimedValue { serdeProvider.serialise(model) }
            results += duration.inWholeNanoseconds
        }
        return BenchmarkResult(
            iterations = iterations,
            durationNanos = results
        )
    }

    fun <T, Z : Any> deserialise(
        serdeProvider: SerdeProvider<T, Z>,
        iterations: Int = 1_000_000,
        modelProvider: () -> Any,
    ): BenchmarkResult {
        val results = mutableListOf<Long>()
        val model = modelProvider.invoke()
        val serialised = serdeProvider.serialise(model)
        repeat(iterations) {
            val (_, duration) = measureTimedValue { serdeProvider.deserialise(serialised, model.javaClass) }
            results += duration.inWholeNanoseconds
        }
        return BenchmarkResult(
            iterations = iterations,
            durationNanos = results
        )
    }

    data class BenchmarkResult(
        val iterations: Int,
        val average: Double,
        val first: Long,
        val min: Long,
        val max: Long
    ) {

        constructor(iterations: Int, durationNanos: List<Long>) : this(
            iterations = iterations,
            average = durationNanos.average(),
            first = durationNanos.firstOrNull() ?: 0,
            min = durationNanos.minOrNull() ?: 0,
            max = durationNanos.maxOrNull() ?: 0,
        )
    }
}