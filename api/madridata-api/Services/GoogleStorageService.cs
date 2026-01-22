using Google.Cloud.Storage.V1;
using Microsoft.Extensions.Configuration;

namespace madridata_api.Services;

public class GoogleStorageService : IStorageService
{
    private const string DefaultImagePath = "players/default.png";
    
    private readonly string _credentialsPath;
    private readonly string _bucketName;
    private readonly TimeSpan _defaultExpiration;

    public GoogleStorageService(IConfiguration configuration)
    {
        var credentialsPath = configuration["GoogleCloudStorage:CredentialsPath"];
        var bucketName = configuration["GoogleCloudStorage:BucketName"] 
            ?? throw new InvalidOperationException("GoogleCloudStorage:BucketName is not configured");

        if (string.IsNullOrEmpty(credentialsPath))
        {
            throw new InvalidOperationException("GoogleCloudStorage:CredentialsPath is not configured");
        }

        if (!File.Exists(credentialsPath))
        {
            throw new FileNotFoundException($"Credentials file not found: {credentialsPath}");
        }

        _credentialsPath = credentialsPath;
        _bucketName = bucketName;
        _defaultExpiration = TimeSpan.FromHours(1);
    }

    public async Task<string> GetImageUrlAsync(string imagePath)
    {
        if (string.IsNullOrEmpty(imagePath))
        {
            imagePath = DefaultImagePath;
        }

        // Remove leading slash if present
        var normalizedPath = imagePath.TrimStart('/');

        var urlSigner = UrlSigner.FromCredentialFile(_credentialsPath);

        var signedUrl = await urlSigner.SignAsync(
            _bucketName,
            normalizedPath,
            _defaultExpiration,
            HttpMethod.Get);

        return signedUrl;
    }
}
