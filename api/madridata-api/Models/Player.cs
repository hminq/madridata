namespace madridata_api.Models;

public class Player
{
    public Guid Id { get; set; }
    public long ProviderPlayerId { get; set; }
    public string? ImageUrl { get; set; }
    public bool IsActive { get; set; } = true;
    public DateTimeOffset CreatedAt { get; set; }
    public DateTimeOffset UpdatedAt { get; set; }
    public ICollection<User> FavoritedByUsers { get; set; } = new List<User>();
}
