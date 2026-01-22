using madridata_api.Models;

namespace madridata_api.Repositories;

public interface IPlayerRepository
{
    Task<Dictionary<long, string>> GetImageUrlsByProviderIdsAsync(IEnumerable<long> providerPlayerIds);
}
