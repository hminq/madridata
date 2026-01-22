using madridata_api.Services;
using madridata_api.Models;
using madridata_api.Repositories;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();

// Register DbContext
builder.Services.AddDbContext<MadridataDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("Default")));

// Register Repositories
builder.Services.AddScoped<IPlayerRepository, PlayerRepository>();

// Register Storage service
builder.Services.AddScoped<IStorageService, GoogleStorageService>();

// Register Squad service
builder.Services.AddScoped<ISquadService, SquadService>();

// Register HttpClient for FootballApi 
builder.Services.AddHttpClient("FootballApi", (serviceProvider, client) =>
{
    var baseUrl = builder.Configuration["FootballApi:BaseUrl"];
    var apiKey = builder.Configuration["FootballApi:ApiKey"];
    
    if (!string.IsNullOrEmpty(baseUrl))
    {
        client.BaseAddress = new Uri(baseUrl);
    }
    
    if (!string.IsNullOrEmpty(apiKey))
    {
        client.DefaultRequestHeaders.Add("x-rapidapi-key", apiKey);
        client.DefaultRequestHeaders.Add("x-rapidapi-host", "v3.football.api-sports.io");
    }
});

var app = builder.Build();

// Configure the HTTP request pipeline.

app.UseStaticFiles();

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
