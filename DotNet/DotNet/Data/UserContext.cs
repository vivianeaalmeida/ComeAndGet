using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using DotNet.Models;

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
    }
}
