using DotNet.DTOs;
using DotNet.Models;
using Microsoft.AspNetCore.Mvc;

/// <summary>
/// Handles account-related operations, such as registration, role management, and login.
/// </summary>
[Route("api/v1/[controller]")]
[ApiController]
public class AccountsController : ControllerBase {
    private readonly AccountService accountService;

    /// <summary>
    /// Initializes a new instance of the <see cref="AccountsController"/> class.
    /// </summary>
    /// <param name="accountService">The account service instance.</param>
    public AccountsController(AccountService accountService) {
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
        if (!result.Succeeded) 
        {
            return BadRequest(result.Errors);
        }
        return Ok(new { message = "User registered successfully." });
    }

    /// <summary>
    /// Logs in a user.
    /// </summary>
    /// <param name="model">The login model.</param>
    /// <returns>A JSON response containing the login result, or an unauthorized response if the login fails.</returns>
    [HttpPost("login")]
    public async Task<IActionResult> Login([FromBody] Login model) {
        var loginResponse = await accountService.LoginAsync(model);
        if (loginResponse == null) 
        {
            return Unauthorized();
        }
        return Ok(loginResponse);
    }
}
