using System.Text.Json.Serialization;

namespace madridata_api.Dtos.SquadPlayers;

public class SquadDto
{
    [JsonPropertyName("players")]
    public List<SquadPlayerDto> Players { get; set; } = new();
}
