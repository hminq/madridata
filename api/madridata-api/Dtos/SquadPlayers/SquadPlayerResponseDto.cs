namespace madridata_api.Dtos.SquadPlayers;

public class SquadPlayerResponseDto
{
    public int Id { get; set; }
    public string Name { get; set; } = string.Empty;
    public int? Number { get; set; }
    public Position Position { get; set; }
    public string ImageUrl { get; set; } = string.Empty;
}
