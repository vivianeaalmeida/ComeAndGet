using DotNet.Models;
using Microsoft.AspNetCore.Identity;

namespace DotNet.SeedClasses {
    public class SeedData {
        public static void Initialize(IServiceProvider serviceProvider, UserManager<ApplicationUser> userManager, RoleManager<IdentityRole> roleManager) {
            var roleName = "Admin";
            var roleExist = roleManager.RoleExistsAsync(roleName).Result;
            if (!roleExist) {
                var role = new IdentityRole(roleName);
                var roleResult = roleManager.CreateAsync(role).Result;
            }


            var role2Name = "User";
            var role2Exist = roleManager.RoleExistsAsync(role2Name).Result;
            if (!role2Exist) {
                var role = new IdentityRole(role2Name);
                var role2Result = roleManager.CreateAsync(role).Result;
            }
        }
    }

}
