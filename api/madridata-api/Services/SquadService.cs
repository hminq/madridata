using System.Linq;
using System.Text.Json;
using Microsoft.Extensions.Configuration;
using madridata_api.Dtos.SquadPlayers;
using madridata_api.Repositories;

namespace madridata_api.Services;

public class SquadService : ISquadService
{
    private const string SquadEndpointTemplate = "/players/squads?team=";
    
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly IConfiguration _configuration;
    private readonly IPlayerRepository _playerRepository;
    private readonly IStorageService _storageService;
    private readonly JsonSerializerOptions _jsonOptions;
    private readonly int _teamId;

    public SquadService(
        IHttpClientFactory httpClientFactory, 
        IConfiguration configuration,
        IPlayerRepository playerRepository,
        IStorageService storageService)
    {
        _httpClientFactory = httpClientFactory;
        _configuration = configuration;
        _playerRepository = playerRepository;
        _storageService = storageService;
        _teamId = _configuration.GetValue<int>("FootballApi:TeamId");
        _jsonOptions = new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        };
    }

    public async Task<List<SquadPlayerResponseDto>> GetTeamSquadAsync()
    {
        var client = _httpClientFactory.CreateClient("FootballApi");
        var endpoint = $"{SquadEndpointTemplate}{_teamId}";
        var response = await client.GetAsync(endpoint);

        response.EnsureSuccessStatusCode();

        var jsonContent = await response.Content.ReadAsStringAsync();
        var apiResponse = JsonSerializer.Deserialize<ApiResponseDto<SquadDto>>(jsonContent, _jsonOptions);

        var apiPlayers = apiResponse?.Response.FirstOrDefault()?.Players ?? new List<SquadPlayerDto>();
        
        if (!apiPlayers.Any())
        {
            return new List<SquadPlayerResponseDto>();
        }

        // Get player Id from api response
        var providerPlayerIds = apiPlayers.Select(p => (long)p.Id).ToList();
        
        // Get image urls from database
        var imageUrlMap = await _playerRepository.GetImageUrlsByProviderIdsAsync(providerPlayerIds);
        
        // Map to response DTO and generate signed URLs
        var result = new List<SquadPlayerResponseDto>();
        
        foreach (var apiPlayer in apiPlayers)
        {
            var providerId = (long)apiPlayer.Id;
            var imagePath = imageUrlMap.TryGetValue(providerId, out var path) && !string.IsNullOrEmpty(path)
                ? path
                : "players/default.png";

            // Generate image URL from storage path
            string fullImageUrl;
            try
            {
                fullImageUrl = await _storageService.GetImageUrlAsync(imagePath);
            }
            catch
            {
                // Fallback to default if generation fails
                try
                {
                    fullImageUrl = await _storageService.GetImageUrlAsync("players/default.png");
                }
                catch
                {
                    fullImageUrl = string.Empty;
                }
            }

            result.Add(new SquadPlayerResponseDto
            {
                Id = apiPlayer.Id,
                Name = apiPlayer.Name,
                Number = apiPlayer.Number,
                Position = apiPlayer.Position,
                ImageUrl = fullImageUrl
            });
        }

        return result;
    }
}
