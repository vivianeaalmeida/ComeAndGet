using DotNet.DTOs;
using DotNet.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;

[Route("api/[controller]")]
[ApiController]
public class AccountController : ControllerBase {
    private readonly UserManager<ApplicationUser> userManager;
    private readonly RoleManager<IdentityRole> roleManager;
    private readonly IConfiguration configuration;

    public AccountController(UserManager<ApplicationUser> userManager,
        RoleManager<IdentityRole> roleManager, IConfiguration configuration) {
        this.userManager = userManager;
        this.roleManager = roleManager;
        this.configuration = configuration;
    }

    [HttpPost("register")]
    public async Task<IActionResult> Register([FromBody] Register model) {
        var user = new ApplicationUser {
            UserName = model.Username,
            Email = model.Email,
            Name = model.Name,
            PhoneNumber = model.PhoneNumber,
            Address = model.Address,
        };

        // Cria o usuário
        var result = await userManager.CreateAsync(user, model.Password);
        if (!result.Succeeded) {
            return BadRequest(result.Errors);
        }

        string roleToAssign = "User";
        if (!string.IsNullOrEmpty(model.Role) && model.Role.Equals("Admin", StringComparison.OrdinalIgnoreCase)) {
            roleToAssign = "User";
        }

        // Atribui o papel "User" ao novo usuário
        var roleResult = await userManager.AddToRoleAsync(user, roleToAssign);
        if (!roleResult.Succeeded) {
            return BadRequest(roleResult.Errors);
        }

        return Ok(new { message = "User registered successfully." });
    }



    [HttpPost("add-role")]
    public async Task<IActionResult> AddRole([FromBody] string role) {
        if (!await roleManager.RoleExistsAsync(role)) {
            var result = await roleManager.CreateAsync(new IdentityRole(role));
            if (result.Succeeded) {
                return Ok(new { message = "Role added successfully" });
            }

            return BadRequest(result.Errors);
        }

        return BadRequest(new { message = "Role already exists." });
    }

    [HttpPost("assign-role")]
    public async Task<IActionResult> AssignRole([FromBody] UserRole model) {
        var user = await userManager.FindByNameAsync(model.Username);
        if (user == null) {
            return BadRequest("ApplicationUser not found");
        }

        var result = await userManager.AddToRoleAsync(user, model.Role);
        if (result.Succeeded) {
            return Ok(new { message = "Role assigned successfully" });
        }

        return BadRequest(result.Errors);
    }


    [HttpPost("login")]
    public async Task<IActionResult> Login([FromBody] Login model) {
        // Buscar usuário pelo e-mail
        var user = await userManager.FindByEmailAsync(model.Email);

        if (user != null && await userManager.CheckPasswordAsync(user, model.Password)) {
            var userRoles = await userManager.GetRolesAsync(user);

            // Criar as claims para o token
            var authClaims = new List<Claim> {
                new Claim(ClaimTypes.NameIdentifier, user.Id),
                new Claim(ClaimTypes.Name, user.Name ?? ""),
                new Claim(ClaimTypes.Email, user.Email ?? ""),
                new Claim(ClaimTypes.MobilePhone, user.PhoneNumber ?? ""), // Número de telefone
                new Claim("Address", user.Address ?? ""), // Endereço como claim personalizada
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString())
            };


            // Adicionar todas as roles do usuário
            foreach (var role in userRoles) {
                authClaims.Add(new Claim(ClaimTypes.Role, role));
            }

            // Gerar o token JWT
            var token = new JwtSecurityToken(
                issuer: configuration["Jwt:Issuer"],
                audience: configuration["Jwt:Audience"],
                expires: DateTime.UtcNow.AddMinutes(double.Parse(configuration["Jwt:ExpiryMinutes"]!)),
                claims: authClaims,
                signingCredentials: new SigningCredentials(
                    new SymmetricSecurityKey(System.Text.Encoding.UTF8.GetBytes(configuration["Jwt:Key"]!)),
                    SecurityAlgorithms.HmacSha256)
            );

            // Criar o objeto de resposta
            var loginResponse = new LoginResponse {
                Token = new JwtSecurityTokenHandler().WriteToken(token),
                Role = userRoles.FirstOrDefault() ?? "User"
            };

            return Ok(loginResponse);
        }

        return Unauthorized();
    }

}
