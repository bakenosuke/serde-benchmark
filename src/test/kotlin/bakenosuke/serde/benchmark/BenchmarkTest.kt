package bakenosuke.serde.benchmark

import bakenosuke.serde.benchmark.SerdeBenchmarker.BenchmarkResult
import bakenosuke.serde.benchmark.gson.GsonJsonSerdeProvider
import bakenosuke.serde.benchmark.jackson.JacksonJsonSerdeProvider
import bakenosuke.serde.benchmark.kotlinx.KotlinxJsonSerdeProvider
import bakenosuke.serde.benchmark.kotlinx.KotlinxProtoBufSerdeProvider
import bakenosuke.serde.benchmark.moshi.MoshiJsonSerdeProvider
import bakenosuke.serde.benchmark.util.Random.randomSimpleDto
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class BenchmarkTest {

    private val columnSize = 20
    private val iterations = 1_000_000
    private val benchmarker = SerdeBenchmarker()

    private val providers = listOf(
        GsonJsonSerdeProvider(),
        JacksonJsonSerdeProvider(),
        KotlinxJsonSerdeProvider(),
        KotlinxProtoBufSerdeProvider(),
        MoshiJsonSerdeProvider(),
    )

    @Test
    fun `init`() {
        // when
        val results = providers.associate { provider ->
            provider.name() to benchmarker.init(serdeProvider = provider, iterations = iterations)
        }

        // then
        results.printAsTable("Initialisation")
    }

    @Test
    fun `serialise - simple`() {
        // when
        val results = providers.associate { provider ->
            provider.name() to benchmarker.serialise(serdeProvider = provider, iterations = iterations) {
                randomSimpleDto()
            }
        }

        // then
        results.printAsTable("Serialisation (Simple)")
    }

    @Test
    fun `deserialise - simple`() {
        // when
        val results = providers.associate { provider ->
            provider.name() to benchmarker.deserialise(serdeProvider = provider, iterations = iterations) {
                randomSimpleDto()
            }
        }

        // then
        results.printAsTable("Deserialisation (Simple)")
    }

    @Test
    fun `serialise - simple collection`() {
        // when
        val data = List(100) {
            randomSimpleDto()
        }.toTypedArray()
        val results = providers.associate { provider ->
            provider.name() to benchmarker.serialise(serdeProvider = provider, iterations = iterations) {
                data
            }
        }

        // then
        results.printAsTable("Serialisation (Simple Collection)")
    }

    @Test
    fun `deserialise - simple collection`() {
        // when
        val data = List(100) {
            randomSimpleDto()
        }.toTypedArray()
        val results = providers.associate { provider ->
            provider.name() to benchmarker.deserialise(serdeProvider = provider, iterations = iterations) {
                data
            }
        }

        // then
        results.printAsTable("Deserialisation (Simple Collection)")
    }

    private fun Map<String, BenchmarkResult>.printAsTable(action: String) {
        println(action)
        println(
            "| ${"Provider".padStart(columnSize)} " +
                    "| ${"Min".padStart(columnSize)} " +
                    "| ${"Max".padStart(columnSize)} " +
                    "| ${"Average".padStart(columnSize)} " +
                    "| ${"First".padStart(columnSize)} " +
                    "|"
        )
        forEach { (name, result) ->
            println(
                "| ${name.padStart(columnSize)} " +
                        "| ${result.min.toString().padStart(columnSize)} " +
                        "| ${result.max.toString().padStart(columnSize)} " +
                        "| ${result.average.toString().padStart(columnSize)} " +
                        "| ${result.first.toString().padStart(columnSize)} " +
                        "|"
            )
        }
    }


}