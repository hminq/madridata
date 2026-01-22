using madridata_api.Dtos.SquadPlayers;

namespace madridata_api.Services;

public interface ISquadService
{
    Task<List<SquadPlayerResponseDto>> GetTeamSquadAsync();
}
