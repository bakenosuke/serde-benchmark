package bakenosuke.serde.benchmark

import bakenosuke.serde.benchmark.SerdeBenchmarker.BenchmarkResult
import bakenosuke.serde.benchmark.gson.GsonJsonSerdeProvider
import bakenosuke.serde.benchmark.jackson.JacksonJsonSerdeProvider
import bakenosuke.serde.benchmark.kotlinx.KotlinxProtoBufSerdeProvider
import bakenosuke.serde.benchmark.kotlinx.KotlinxJsonSerdeProvider
import bakenosuke.serde.benchmark.model.SimpleDto
import bakenosuke.serde.benchmark.moshi.MoshiJsonSerdeProvider
import com.tyro.oss.randomdata.RandomBoolean
import com.tyro.oss.randomdata.RandomDouble
import com.tyro.oss.randomdata.RandomInteger
import com.tyro.oss.randomdata.RandomString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

class BenchmarkTest {

    private val columnSize = 20
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
            provider.name() to benchmarker.init(serdeProvider = provider)
        }

        // then
        results.printAsTable("Initialisation")
    }

    @Test
    fun `serialise - simple`() {
        // when
        val results = providers.associate { provider ->
            provider.name() to benchmarker.serialise(serdeProvider = provider) {
                SimpleDto(
                    id = RandomInteger.randomInteger(),
                    name = RandomString.randomAlphanumericString(),
                    description = RandomString.randomAlphanumericString(),
                    value = RandomDouble.randomDouble(),
                    enabled = RandomBoolean.randomBoolean(),
                )
            }
        }

        // then
        results.printAsTable("Serialisation (Simple)")
    }

    @Test
    fun `deserialise - simple`() {
        // when
        val results = providers.associate { provider ->
            provider.name() to benchmarker.deserialise(serdeProvider = provider) {
                SimpleDto(
                    id = RandomInteger.randomInteger(),
                    name = RandomString.randomAlphanumericString(),
                    description = RandomString.randomAlphanumericString(),
                    value = RandomDouble.randomDouble(),
                    enabled = RandomBoolean.randomBoolean(),
                )
            }
        }

        // then
        results.printAsTable("Deserialisation (Simple)")
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
        forEach { name, result ->
            println(
                "| ${name.padStart(columnSize)} | ${
                    result.min.toString().padStart(columnSize)
                } | ${result.max.toString().padStart(columnSize)} | ${
                    result.average.toString().padStart(columnSize)
                } | ${result.first.toString().padStart(columnSize)} |"
            )
        }
    }


}