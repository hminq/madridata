package hminq.dev.madridata.data.repository

import hminq.dev.madridata.data.remote.PlayerApiService
import hminq.dev.madridata.domain.model.OnboardingFavPlayer
import hminq.dev.madridata.domain.repository.OnboardingFavPlayerRepository
import javax.inject.Inject

class OnboardingFavPlayerRepositoryImpl @Inject constructor(
    private val playerApiService: PlayerApiService
) : OnboardingFavPlayerRepository {

    override suspend fun getPlayers(): Result<List<OnboardingFavPlayer>> = runCatching {
        playerApiService.getPlayers().map { it.toDomain() }
    }
}
