using DotNet.DTOs;
using DotNet.Exceptions;
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
        try {
            var result = await accountService.RegisterUserAsync(model);
            if (!result.Succeeded) {
                return BadRequest(result.Errors);
            }
            return Created("", new { message = "User registered successfully." });
        }
        catch (EmailInUseException ex) {
            return Conflict(new { message = ex.Message });
        }
        catch (UsernameInUseException ex) {
            return Conflict(new { message = ex.Message });
        } catch (Exception ex) {
            return Conflict(new { message = "An unexpected error occurred." });
        }
    }

    /// <summary>
    /// Logs in a user.
    /// </summary>
    /// <param name="model">The login model.</param>
    /// <returns>A JSON response containing the login result, or an unauthorized response if the login fails.</returns>
    [HttpPost("login")]
    public async Task<IActionResult> Login([FromBody] Login model) {
        try {
            var loginResponse = await accountService.LoginAsync(model);
            return Ok(loginResponse);
        }
        catch (InvalidLoginException ex) {
            return Unauthorized(new { message = ex.Message });
        } catch (Exception ex) {
            return Conflict(new { message = "An unexpected error occurred." });
        }
    }
}
