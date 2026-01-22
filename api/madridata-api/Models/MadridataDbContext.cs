using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;

namespace madridata_api.Models;

public class MadridataDbContext : DbContext
{
    private readonly IConfiguration _configuration;

    public DbSet<User> Users => Set<User>();
    public DbSet<Player> Players => Set<Player>();

    public MadridataDbContext(DbContextOptions<MadridataDbContext> options, IConfiguration configuration)
        : base(options)
    {
        _configuration = configuration;
    }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Player>(entity =>
        {
            entity.ToTable("players");

            entity.HasKey(e => e.Id);

            entity.Property(e => e.Id)
                .HasColumnName("id");

            entity.Property(e => e.ProviderPlayerId)
                .HasColumnName("provider_player_id");

            var defaultImageUrl = _configuration["FirebaseStorage:DefaultPlayerImageUrl"] ?? "";
            entity.Property(e => e.ImageUrl)
                .HasColumnName("image_url")
                .HasDefaultValue(defaultImageUrl);

            entity.Property(e => e.IsActive)
                .HasColumnName("is_active")
                .HasDefaultValue(true);

            entity.Property(e => e.CreatedAt)
                .HasColumnName("created_at")
                .HasDefaultValueSql("now()");

            entity.Property(e => e.UpdatedAt)
                .HasColumnName("updated_at")
                .HasDefaultValueSql("now()");
        });

        modelBuilder.Entity<User>(entity =>
        {
            entity.ToTable("users");

            entity.HasKey(e => e.Id);

            entity.Property(e => e.Id)
                .HasColumnName("id");

            entity.Property(e => e.Email)
                .HasColumnName("email")
                .HasMaxLength(255);

            entity.Property(e => e.PasswordHash)
                .HasColumnName("password_hash");

            entity.Property(e => e.GoogleSub)
                .HasColumnName("google_sub")
                .HasMaxLength(255);

            entity.Property(e => e.DisplayName)
                .HasColumnName("display_name")
                .HasMaxLength(100);

            entity.Property(e => e.FavoritePlayerId)
                .HasColumnName("favorite_player_id");

            entity.Property(e => e.Score)
                .HasColumnName("score")
                .HasDefaultValue(0);

            entity.Property(e => e.CreatedAt)
                .HasColumnName("created_at")
                .HasDefaultValueSql("now()");

            entity.Property(e => e.UpdatedAt)
                .HasColumnName("updated_at")
                .HasDefaultValueSql("now()");

            entity.HasOne(e => e.FavoritePlayer)
                .WithMany(p => p.FavoritedByUsers)
                .HasForeignKey(e => e.FavoritePlayerId)
                .OnDelete(DeleteBehavior.SetNull);
        });
    }
}
