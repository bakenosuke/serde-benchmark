package bakenosuke.serde.benchmark.model

import kotlinx.serialization.Serializable

@Serializable
data class SimpleDto(
    val id: Int,
    val name: String,
    val description: String,
    val value: Double,
    val enabled: Boolean,
)
