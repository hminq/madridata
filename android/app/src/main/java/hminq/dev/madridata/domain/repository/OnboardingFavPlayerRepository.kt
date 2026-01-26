package hminq.dev.madridata.domain.repository

import hminq.dev.madridata.domain.model.OnboardingFavPlayer

interface OnboardingFavPlayerRepository {
    suspend fun getPlayers(): Result<List<OnboardingFavPlayer>>
}
