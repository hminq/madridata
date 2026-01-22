using Microsoft.EntityFrameworkCore;
using madridata_api.Models;

namespace madridata_api.Repositories;

public class PlayerRepository : IPlayerRepository
{
    private readonly MadridataDbContext _context;

    public PlayerRepository(MadridataDbContext context)
    {
        _context = context;
    }

    public async Task<Dictionary<long, string>> GetImageUrlsByProviderIdsAsync(IEnumerable<long> providerPlayerIds)
    {
        var players = await _context.Players
            .Where(p => providerPlayerIds.Contains(p.ProviderPlayerId))
            .Select(p => new { p.ProviderPlayerId, p.ImageUrl })
            .ToListAsync();

        return players.ToDictionary(
            p => p.ProviderPlayerId,
            p => p.ImageUrl ?? string.Empty
        );
    }
}
