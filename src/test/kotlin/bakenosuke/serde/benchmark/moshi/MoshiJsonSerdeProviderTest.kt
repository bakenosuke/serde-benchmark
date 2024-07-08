package bakenosuke.serde.benchmark.moshi

import bakenosuke.serde.benchmark.kotlinx.KotlinxJsonSerdeProvider
import bakenosuke.serde.benchmark.model.SimpleDto
import bakenosuke.serde.benchmark.util.Random
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MoshiJsonSerdeProviderTest {

    private val provider = MoshiJsonSerdeProvider()

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