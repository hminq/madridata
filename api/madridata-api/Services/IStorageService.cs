namespace madridata_api.Services;

public interface IStorageService
{
    Task<string> GetImageUrlAsync(string imagePath);
}
