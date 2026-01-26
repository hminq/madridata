package hminq.dev.madridata.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hminq.dev.madridata.data.repository.OnboardingFavPlayerRepositoryImpl
import hminq.dev.madridata.domain.repository.OnboardingFavPlayerRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindOnboardingFavPlayerRepository(
        impl: OnboardingFavPlayerRepositoryImpl
    ): OnboardingFavPlayerRepository
}
