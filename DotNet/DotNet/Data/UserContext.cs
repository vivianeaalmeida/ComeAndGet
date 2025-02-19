using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using DotNet.Models;
using Microsoft.AspNetCore.Identity;

namespace DotNet.Data {
    /// <summary>
    /// Represents the database context for user-related data.
    /// </summary>
    public class UserContext : IdentityDbContext<ApplicationUser> {

        /// <summary>
        /// Initializes a new instance of the <see cref="UserContext"/> class.
        /// </summary>
        /// <param name="options">The options for the database context.</param>
        public UserContext(DbContextOptions<UserContext> options) : base(options) { }


        //protected override void OnModelCreating(ModelBuilder modelBuilder) {
        //    base.OnModelCreating(modelBuilder);

        //    // Limitar o tamanho do índice de NormalizedName
        //    modelBuilder.Entity<IdentityRole>()
        //        .Property(r => r.NormalizedName)
        //        .HasMaxLength(191); // Limite do índice para IdentityRole

        //    // Limitar o tamanho do índice de NormalizedUserName
        //    modelBuilder.Entity<ApplicationUser>()
        //        .Property(u => u.NormalizedUserName)
        //        .HasMaxLength(191); // Limite do índice para AspNetUsers
        //}

    }
}
