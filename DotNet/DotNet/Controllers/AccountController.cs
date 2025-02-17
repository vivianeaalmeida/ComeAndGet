﻿using DotNet.DTOs;
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
            Address = model.Address
        };
        var result = await userManager.CreateAsync(user, model.Password);

        if (result.Succeeded) {
            return Ok(new { message = "User registered successfully." });
        }
        return BadRequest(result.Errors);
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

        return BadRequest("Role already exists");
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
        var user = await userManager.FindByNameAsync(model.Username);
        if (user != null && await userManager.CheckPasswordAsync(user, model.Password)) {
            var userRoles = await userManager.GetRolesAsync(user);

            var authClaims = new List<Claim>
            {
                    new Claim(JwtRegisteredClaimNames.Sub, user.UserName!),
                    new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString())
                };

            authClaims.AddRange(userRoles.Select(role => new Claim(ClaimTypes.Role, role)));

            var token = new JwtSecurityToken(
                issuer: configuration["Jwt:Issuer"],
                expires: DateTime.Now.AddMinutes(double.Parse(configuration["Jwt:ExpiryMinutes"]!)),
                claims: authClaims,
                signingCredentials: new SigningCredentials(new SymmetricSecurityKey(System.Text.Encoding.UTF8.GetBytes(configuration["Jwt:Key"]!)),
                SecurityAlgorithms.HmacSha256));

            return Ok(new { token = new JwtSecurityTokenHandler().WriteToken(token) });
        }

        return Unauthorized();
    }
}
