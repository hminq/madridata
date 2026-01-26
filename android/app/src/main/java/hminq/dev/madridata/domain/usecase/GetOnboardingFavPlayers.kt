package hminq.dev.madridata.domain.usecase

import hminq.dev.madridata.domain.model.OnboardingFavPlayer
import hminq.dev.madridata.domain.repository.OnboardingFavPlayerRepository
import javax.inject.Inject

class GetOnboardingFavPlayers @Inject constructor(
    private val repository: OnboardingFavPlayerRepository
) {
    suspend operator fun invoke(): Result<List<OnboardingFavPlayer>> = repository.getPlayers()
}
