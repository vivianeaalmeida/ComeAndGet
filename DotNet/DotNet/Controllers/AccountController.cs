using DotNet.DTOs;
using DotNet.Models;
using Microsoft.AspNetCore.Mvc;

/// <summary>
/// Handles account-related operations, such as registration, role management, and login.
/// </summary>
[Route("api/v1/[controller]")]
[ApiController]
public class AccountController : ControllerBase {
    private readonly AccountService accountService;

    /// <summary>
    /// Initializes a new instance of the <see cref="AccountController"/> class.
    /// </summary>
    /// <param name="accountService">The account service instance.</param>
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /// <summary>
    /// Registers a new user.
    /// </summary>
    /// <param name="model">The registration model.</param>
    /// <returns>A JSON response indicating whether the registration was successful.</returns>
    [HttpPost("register")]
    public async Task<IActionResult> Register([FromBody] Register model) {
        var result = await accountService.RegisterUserAsync(model);
        if (!result.Succeeded) {
            return BadRequest(result.Errors);
        }
        return Ok(new { message = "User registered successfully." });
    }

    /// <summary>
    /// Adds a new role to the system.
    /// </summary>
    /// <param name="role">The name of the role to add.</param>
    /// <returns>A JSON response indicating whether the role was added successfully.</returns>
<<<<<<< HEAD
    //[HttpPost("add-role")]
    //public async Task<IActionResult> AddRole([FromBody] string role) {
    //    var result = await accountService.AddRoleAsync(role);
    //    if (result.Succeeded) {
    //        return Ok(new { message = "Role added successfully" });
    //    }
    //    return BadRequest(result.Errors);
    //}
=======
    [HttpPost("add-role")]
    public async Task<IActionResult> AddRole([FromBody] string role) {
        var result = await accountService.AddRoleAsync(role);
        if (result.Succeeded) {
            return Ok(new { message = "Role added successfully" });
        }
        return BadRequest(result.Errors);
    }
>>>>>>> 1f8e1f76528e583562cc47b6c477cbab466ac446

    /// <summary>
    /// Assigns a role to a user.
    /// </summary>
    /// <param name="model">The user-role assignment model.</param>
    /// <returns>A JSON response indicating whether the role was assigned successfully.</returns>
<<<<<<< HEAD
    //[HttpPost("assign-role")]
    //public async Task<IActionResult> AssignRole([FromBody] UserRoleDTO model) {
    //    var result = await accountService.AssignRoleAsync(model);
    //    if (result.Succeeded) {
    //        return Ok(new { message = "Role assigned successfully" });
    //    }
    //    return BadRequest(result.Errors);
    //}

    /// <summary>
    /// Logs in a user.
    /// </summary>
    /// <param name="model">The login model.</param>
    /// <returns>A JSON response containing the login result, or an unauthorized response if the login fails.</returns>
    [HttpPost("login")]
    public async Task<IActionResult> Login([FromBody] Login model) {
        var loginResponse = await accountService.LoginAsync(model);
        if (loginResponse == null) {
            return Unauthorized();
        }
=======
    [HttpPost("assign-role")]
    public async Task<IActionResult> AssignRole([FromBody] UserRoleDTO model) {
        var result = await accountService.AssignRoleAsync(model);
        if (result.Succeeded) {
            return Ok(new { message = "Role assigned successfully" });
        }
        return BadRequest(result.Errors);
    }

    /// <summary>
    /// Logs in a user.
    /// </summary>
    /// <param name="model">The login model.</param>
    /// <returns>A JSON response containing the login result, or an unauthorized response if the login fails.</returns>
    [HttpPost("login")]
    public async Task<IActionResult> Login([FromBody] Login model) {
        var loginResponse = await accountService.LoginAsync(model);
        if (loginResponse == null) {
            return Unauthorized();
        }
>>>>>>> 1f8e1f76528e583562cc47b6c477cbab466ac446
        return Ok(loginResponse);
    }
}
