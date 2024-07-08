package bakenosuke.serde.benchmark.util

import bakenosuke.serde.benchmark.model.SimpleDto
import com.tyro.oss.randomdata.RandomBoolean
import com.tyro.oss.randomdata.RandomDouble
import com.tyro.oss.randomdata.RandomInteger
import com.tyro.oss.randomdata.RandomString

object Random {

    fun randomSimpleDto(): SimpleDto {
        return SimpleDto(
            id = RandomInteger.randomInteger(),
            name = RandomString.randomAlphanumericString(),
            description = RandomString.randomAlphanumericString(),
            value = RandomDouble.randomDouble(),
            enabled = RandomBoolean.randomBoolean(),
        )
    }

}