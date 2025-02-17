using System.Linq;
using System.Security.Claims;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using DotNet.DTOs; // Importando o DTO

namespace DotNet.Controllers {
    [Authorize(Roles = "Admin")]
    [Route("api/[controller]")]
    [ApiController]
    public class AdminController : ControllerBase {
        [HttpGet]
        public IActionResult Get() {
            var userName = User.Identity?.Name;
            var email = User.FindFirst(ClaimTypes.Email)?.Value;
            var name = User.FindFirst(ClaimTypes.Name)?.Value;
            var phoneNumber = User.FindFirst(ClaimTypes.MobilePhone)?.Value;
            var address = User.FindFirst("Address")?.Value; // Claim personalizada, se existir
            var role = User.Claims.FirstOrDefault(c => c.Type == ClaimTypes.Role)?.Value ?? "Admin";

            var adminInfo = new UserInfoResponse {
                Username = userName ?? string.Empty,
                Email = email ?? string.Empty,
                Name = name ?? string.Empty,
                PhoneNumber = phoneNumber ?? string.Empty,
                Address = address ?? string.Empty,
                Role = role
            };

            return Ok(new {
                Message = "You have accessed the Admin area.",
                AdminInfo = adminInfo
            });
        }
    }
}
