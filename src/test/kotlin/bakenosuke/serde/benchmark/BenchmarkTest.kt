package bakenosuke.serde.benchmark

import bakenosuke.serde.benchmark.SerdeBenchmarker.BenchmarkResult
import bakenosuke.serde.benchmark.gson.GsonSerdeProvider
import bakenosuke.serde.benchmark.jackson.JacksonSerdeProvider
import bakenosuke.serde.benchmark.kotlinx.KotlinxSerdeProvider
import bakenosuke.serde.benchmark.model.SimpleDto
import bakenosuke.serde.benchmark.moshi.MoshiSerdeProvider
import com.tyro.oss.randomdata.RandomBoolean
import com.tyro.oss.randomdata.RandomDouble
import com.tyro.oss.randomdata.RandomInstance
import com.tyro.oss.randomdata.RandomInteger
import com.tyro.oss.randomdata.RandomString
import org.junit.jupiter.api.Test

class BenchmarkTest {

    private val benchmarker = SerdeBenchmarker()

    private val providers = listOf(
        GsonSerdeProvider(),
        JacksonSerdeProvider(),
        KotlinxSerdeProvider(),
        MoshiSerdeProvider(),
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

    private fun Map<String,BenchmarkResult>.printAsTable(action:String) {
        println(action)
        println("| ${"Provider".padStart(15)} | ${"Min".padStart(15)} | ${"Max".padStart(15)} | ${"Average".padStart(15)} | ${"First".padStart(15)}  |")
        forEach { name, result ->
            println("| ${name.padStart(15)} | ${result.min.toString().padStart(15)} | ${result.max.toString().padStart(15)} | ${result.average.toString().padStart(15)} | ${result.first.toString().padStart(15)} |")
        }
    }


}