using System.Text.Json.Serialization;

namespace madridata_api.Dtos.SquadPlayers;

public class ApiResponseDto<T>
{
    [JsonPropertyName("get")]
    public string Get { get; set; } = string.Empty;

    [JsonPropertyName("parameters")]
    public Dictionary<string, string>? Parameters { get; set; }

    [JsonPropertyName("errors")]
    public List<string> Errors { get; set; } = new();

    [JsonPropertyName("results")]
    public int Results { get; set; }

    [JsonPropertyName("paging")]
    public Dictionary<string, int>? Paging { get; set; }

    [JsonPropertyName("response")]
    public List<T> Response { get; set; } = new();
}
