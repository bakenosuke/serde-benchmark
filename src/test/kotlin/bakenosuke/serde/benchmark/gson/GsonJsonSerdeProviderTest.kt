package bakenosuke.serde.benchmark.gson

import bakenosuke.serde.benchmark.model.SimpleDto
import bakenosuke.serde.benchmark.util.Random
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GsonJsonSerdeProviderTest {

    private val provider = GsonJsonSerdeProvider()

    @Test
    fun `should work`() {
        // given
        val model = Random.randomSimpleDto()
        val serialised = provider.serialise(model)

        // when
        val deserialised = provider.deserialise(serialised, SimpleDto::class.java)

        // then
        model shouldBe deserialised
    }
}