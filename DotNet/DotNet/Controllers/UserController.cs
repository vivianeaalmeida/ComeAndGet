using DotNet.DTOs;
using DotNet.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace DotNet.Controllers {
    /// <summary>
    /// Handles user-related operations, such as retrieving user information.
    /// </summary>
    [Authorize(Roles = "User")]
    [Route("api/v1/[controller]")]
    [ApiController]
    public class UserController : ControllerBase {
        private readonly UserService _userService;

        /// <summary>
        /// Initializes a new instance of the <see cref="UserController"/> class.
        /// </summary>
        /// <param name="userService">The instance of UserService.</param>
        public UserController(UserService userService) {
            _userService = userService;
        }

        /// <summary>
        /// Retrieves user information.
        /// </summary>
        /// <returns>A JSON response containing the user information.</returns>
        [HttpGet]
        public IActionResult Get() {
            var userInfo = _userService.GetUserInfo();
            return Ok(userInfo);
        }

        /// <summary>
        /// Retrieves user information by id.
        /// </summary>
        /// <param name="id">User ID</param>
        /// <returns>A JSON response containing the user information.</returns>
        [HttpGet("{id}")]
        public async Task<IActionResult> GetUserById(string id)
        {
            var userInfo = await _userService.GetUserById(id);

            if (userInfo == null)
            {
                return NotFound(new { message = $"User with ID {id} not found." });
            }

            return Ok(userInfo);
        }




        /// <summary>
        /// Retrieves user ID.
        /// </summary>
        /// <returns></returns>
        [HttpGet("id")]
        public IActionResult GetUserId() {
            var id = _userService.GetUserId();
            return Ok(id);
        }
    }
}
