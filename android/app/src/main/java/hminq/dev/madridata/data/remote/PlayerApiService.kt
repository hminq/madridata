package hminq.dev.madridata.data.remote

import hminq.dev.madridata.data.remote.dto.PlayerDto
import retrofit2.http.GET

interface PlayerApiService {
    @GET("squad/players")
    suspend fun getPlayers(): List<PlayerDto>
}
