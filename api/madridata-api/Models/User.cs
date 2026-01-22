using System;

namespace madridata_api.Models;

public class User
{
    public Guid Id { get; set; }
    public string Email { get; set; } = null!;
    public string? PasswordHash { get; set; }
    public string? GoogleSub { get; set; }
    public string DisplayName { get; set; } = null!;
    public Guid? FavoritePlayerId { get; set; }
    public long Score { get; set; } = 0;
    public DateTimeOffset CreatedAt { get; set; }
    public DateTimeOffset UpdatedAt { get; set; }
    public Player? FavoritePlayer { get; set; }
}
