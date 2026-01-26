package hminq.dev.madridata.domain.model

data class OnboardingFavPlayer(
    val id: Int,
    val name: String,
    val number: Int,
    val position: PlayerPosition,
    val imageUrl: String
)

enum class PlayerPosition(val displayName: String) {
    GOALKEEPER("Goalkeeper"),
    DEFENDER("Defender"),
    MIDFIELDER("Midfielder"),
    FORWARD("Forward");

    companion object {
        fun fromInt(value: Int): PlayerPosition = when (value) {
            0 -> GOALKEEPER
            1 -> DEFENDER
            2 -> MIDFIELDER
            3 -> FORWARD
            else -> FORWARD
        }
    }
}
