using System.Linq;
using System.Security.Claims;
using DotNet.DTOs;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace DotNet.Controllers {
    [Authorize(Roles = "User")]
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase {
        [HttpGet]
        public IActionResult Get() {
            var userName = User.Identity?.Name;
            var email = User.FindFirst(ClaimTypes.Email)?.Value;
            var name = User.FindFirst(ClaimTypes.Name)?.Value;
            var phoneNumber = User.FindFirst(ClaimTypes.MobilePhone)?.Value;
            var address = User.FindFirst("Address")?.Value; // Se houver uma claim personalizada de endereço
            var role = User.Claims.FirstOrDefault(c => c.Type == ClaimTypes.Role)?.Value ?? "User";

            var userInfo = new UserInfoResponse {
                Username = userName ?? string.Empty,
                Email = email ?? string.Empty,
                Name = name ?? string.Empty,
                PhoneNumber = phoneNumber ?? string.Empty,
                Address = address ?? string.Empty,
                Role = role
            };

            return Ok(userInfo);
        }
    }
}