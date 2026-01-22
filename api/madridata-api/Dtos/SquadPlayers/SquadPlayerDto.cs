using System.Text.Json.Serialization;

namespace madridata_api.Dtos.SquadPlayers;

public class SquadPlayerDto
{
    [JsonPropertyName("id")]
    public int Id { get; set; }

    [JsonPropertyName("name")]
    public string Name { get; set; } = string.Empty;

    [JsonPropertyName("number")]
    public int? Number { get; set; }

    [JsonPropertyName("position")]
    public string PositionString { get; set; } = string.Empty;

    [JsonIgnore]
    public Position Position
    {
        get
        {
            return PositionString switch
            {
                "Goalkeeper" => Position.Goalkeeper,
                "Defender" => Position.Defender,
                "Midfielder" => Position.Midfielder,
                "Attacker" => Position.Attacker,
                _ => Position.Midfielder 
            };
        }
    }
}
