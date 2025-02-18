using DotNet.DTOs;
using DotNet.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;

/// <summary>
/// Provides methods for managing user accounts and authentication.
/// </summary>
public class AccountService {
    private readonly UserManager<ApplicationUser> userManager;
    private readonly RoleManager<IdentityRole> roleManager;
    private readonly IConfiguration configuration;

    /// <summary>
    /// Initializes a new instance of the <see cref="AccountService"/> class.
    /// </summary>
    /// <param name="userManager">The user manager.</param>
    /// <param name="roleManager">The role manager.</param>
    /// <param name="configuration">The configuration.</param>
    public AccountService(UserManager<ApplicationUser> userManager,
        RoleManager<IdentityRole> roleManager, IConfiguration configuration) {
        this.userManager = userManager;
        this.roleManager = roleManager;
        this.configuration = configuration;
    }

    /// <summary>
    /// Registers a new user.
    /// </summary>
    /// <param name="model">The registration model.</param>
    /// <returns>The result of the registration operation.</returns>
    public async Task<IdentityResult> RegisterUserAsync(Register model) {
        var user = new ApplicationUser {
            UserName = model.Username,
            Email = model.Email,
            Name = model.Name,
            PhoneNumber = model.PhoneNumber,
        };

        var result = await userManager.CreateAsync(user, model.Password);
        if (result.Succeeded) {
            await userManager.AddToRoleAsync(user, "User");
        }

        return result;
    }



    /// <summary>
    /// Adds a new role.
    /// </summary>
    /// <param name="role">The name of the role.</param>
    /// <returns>The result of the role addition operation.</returns>
    public async Task<IdentityResult> AddRoleAsync(string role) {
        if (!await roleManager.RoleExistsAsync(role)) {
            return await roleManager.CreateAsync(new IdentityRole(role));
        }
        return IdentityResult.Failed(new IdentityError { Description = "Roles already exists." });
    }


    /// <summary>
    /// Assigns a role to a user.
    /// </summary>
    /// <param name="model">The user-role model.</param>
    /// <returns>The result of the role assignment operation.</returns>
    public async Task<IdentityResult> AssignRoleAsync(UserRoleDTO model) {
        var user = await userManager.FindByNameAsync(model.Username);
        if (user == null) {
            return IdentityResult.Failed(new IdentityError { Description = "User not found" });
        }

        return await userManager.AddToRoleAsync(user, model.Role);
    }


    /// <summary>
    /// Authenticates a user and generates a JWT token.
    /// </summary>
    /// <param name="model">The login model.</param>
    /// <returns>The login response containing the token and user role.</returns>
    public async Task<LoginResponse> LoginAsync(Login model) {
        var user = await userManager.FindByEmailAsync(model.Email);

        if (user != null && await userManager.CheckPasswordAsync(user, model.Password)) {
            var userRoles = await userManager.GetRolesAsync(user);

            var authClaims = new List<Claim> {
                new Claim(ClaimTypes.NameIdentifier, user.Id),
                new Claim(ClaimTypes.Name, user.Name ?? ""),
                new Claim(ClaimTypes.Email, user.Email ?? ""),
                new Claim(ClaimTypes.MobilePhone, user.PhoneNumber ?? ""),
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString())
            };

            foreach (var role in userRoles) {
                authClaims.Add(new Claim(ClaimTypes.Role, role));
            }

            var token = new JwtSecurityToken(
                issuer: configuration["Jwt:Issuer"],
                audience: configuration["Jwt:Audience"],
                expires: DateTime.UtcNow.AddMinutes(double.Parse(configuration["Jwt:ExpiryMinutes"]!)),
                claims: authClaims,
                signingCredentials: new SigningCredentials(
                    new SymmetricSecurityKey(System.Text.Encoding.UTF8.GetBytes(configuration["Jwt:Key"]!)),
                    SecurityAlgorithms.HmacSha256)
            );

            return new LoginResponse {
                Token = new JwtSecurityTokenHandler().WriteToken(token),
                Roles = userRoles.FirstOrDefault() ?? "User"
            };
        }

        return null;
    }
}
