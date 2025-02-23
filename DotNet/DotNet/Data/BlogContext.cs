using DotNet.Models;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;

namespace DotNet.Data {
    public class BlogContext : DbContext {
        public BlogContext(DbContextOptions<BlogContext> options) : base(options) { }

        public DbSet<Tip> Tips { get; set; }

        public DbSet<Interaction> Interactions { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder) {
            base.OnModelCreating(modelBuilder);
        }

        public async Task SeedDataAsync() {
            Console.WriteLine("Checking if tips need to be seeded...");

            var tipsJson = File.ReadAllText("wwwroot/data/tips.json");
            var tipsFromJson = JsonConvert.DeserializeObject<List<Tip>>(tipsJson);

            foreach (var tip in tipsFromJson) {
                tip.LikeCount = 0;
                tip.FavoriteCount = 0;
            }

            if (tipsFromJson == null || !tipsFromJson.Any()) {
                Console.WriteLine(" JSON file is empty or invalid.");
                return;
            }

            foreach (var tip in tipsFromJson) {
                // Verifica se já existe uma dica igual no banco
                bool exists = await Tips.AnyAsync(t => t.Title == tip.Title && t.Content == tip.Content);

                if (!exists) {
                    await Tips.AddAsync(tip);
                    Console.WriteLine($"Added tip: {tip.Title}");
                }
                else {
                    Console.WriteLine($"Tip already exists: {tip.Title}");
                }
            }

            await SaveChangesAsync();
            Console.WriteLine("Seeding process completed!");
        }

    }


}