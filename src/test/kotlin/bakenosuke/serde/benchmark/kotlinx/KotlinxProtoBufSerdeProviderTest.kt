package bakenosuke.serde.benchmark.kotlinx

import bakenosuke.serde.benchmark.model.SimpleDto
import bakenosuke.serde.benchmark.util.Random
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class KotlinxProtoBufSerdeProviderTest {

    private val provider = KotlinxProtoBufSerdeProvider()

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