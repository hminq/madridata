package hminq.dev.madridata.data.remote.dto

import hminq.dev.madridata.domain.model.OnboardingFavPlayer
import hminq.dev.madridata.domain.model.PlayerPosition

data class PlayerDto(
    val id: Int,
    val name: String,
    val number: Int,
    val position: Int,
    val imageUrl: String
) {
    fun toDomain(): OnboardingFavPlayer = OnboardingFavPlayer(
        id = id,
        name = name,
        number = number,
        position = PlayerPosition.fromInt(position),
        imageUrl = imageUrl
    )
}
