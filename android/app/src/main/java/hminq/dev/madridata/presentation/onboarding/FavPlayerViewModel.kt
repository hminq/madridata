package hminq.dev.madridata.presentation.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hminq.dev.madridata.domain.model.OnboardingFavPlayer
import hminq.dev.madridata.domain.usecase.GetOnboardingFavPlayers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavPlayerViewModel @Inject constructor(
    private val getOnboardingFavPlayers: GetOnboardingFavPlayers
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavPlayerUiState>(FavPlayerUiState.Loading)
    val uiState: StateFlow<FavPlayerUiState> = _uiState.asStateFlow()

    init {
        loadPlayers()
    }

    fun loadPlayers() {
        viewModelScope.launch {
            _uiState.value = FavPlayerUiState.Loading
            getOnboardingFavPlayers()
                .onSuccess { players ->
                    _uiState.value = FavPlayerUiState.Success(players)
                }
                .onFailure { error ->
                    Log.e(TAG, "Failed to load players", error)
                    _uiState.value = FavPlayerUiState.Error(error.message ?: "Unknown error")
                }
        }
    }

    fun onPlayerClicked(player: OnboardingFavPlayer) {
        Log.d(TAG, "Player clicked: id=${player.id}, name=${player.name}, number=${player.number}, position=${player.position.displayName}")
    }

    companion object {
        private const val TAG = "FavPlayerViewModel"
    }
}

sealed interface FavPlayerUiState {
    data object Loading : FavPlayerUiState
    data class Success(val players: List<OnboardingFavPlayer>) : FavPlayerUiState
    data class Error(val message: String) : FavPlayerUiState
}
