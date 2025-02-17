using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using DotNet.Models;

namespace DotNet.Data {
    public class UserContext : IdentityDbContext<ApplicationUser> {
        public UserContext(DbContextOptions<UserContext> options) : base(options) { }
    }
}
