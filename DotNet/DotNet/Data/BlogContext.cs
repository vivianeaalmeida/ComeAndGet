using DotNet.Models;
using Microsoft.EntityFrameworkCore;

namespace DotNet.Data {
    public class BlogContext : DbContext {
        public BlogContext(DbContextOptions options) : base(options) { }

        public DbSet<Tip> Tips { get; set; }

        public DbSet<Interaction> Interactions { get; set; }
    }
}
