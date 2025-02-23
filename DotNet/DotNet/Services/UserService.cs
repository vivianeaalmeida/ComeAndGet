using System.Linq;
using System.Security.Claims;
using DotNet.DTOs;
using DotNet.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;

namespace DotNet.Services {
    /// <summary>
    /// Provides methods for managing user-related operations.
    /// </summary>
    public class UserService {
        private readonly IHttpContextAccessor _httpContextAccessor;
        private readonly UserManager<ApplicationUser> userManager;


        /// <summary>
        /// Initializes a new instance of the <see cref="UserService"/> class.
        /// </summary>
        /// <param name="httpContextAccessor">The IHttpContextAccessor instance.</param>
        /// <param name="userManager">The user manager.</param>
        public UserService(IHttpContextAccessor httpContextAccessor,
            UserManager<ApplicationUser> userManager) {
            _httpContextAccessor = httpContextAccessor;
            this.userManager = userManager;
        }


        /// <summary>
        /// Retrieves information about the current user.
        /// </summary>
        /// <returns>A UserInfoResponseDTO containing the user's information.</returns>
        public UserInfoResponseDTO GetUserInfo() {
            var user = _httpContextAccessor.HttpContext?.User;

            var userId = user?.FindFirst(ClaimTypes.NameIdentifier)?.Value;
            var userName = user?.Identity?.Name;
            var email = user?.FindFirst(ClaimTypes.Email)?.Value;
            var name = user?.FindFirst(ClaimTypes.Name)?.Value;
            var phoneNumber = user?.FindFirst(ClaimTypes.MobilePhone)?.Value;
            var role = user?.Claims.FirstOrDefault(c => c.Type == ClaimTypes.Role)?.Value ?? "User";

            return new UserInfoResponseDTO {
                UserId = userId ?? string.Empty,
                Username = userName ?? string.Empty,
                Email = email ?? string.Empty,
                Name = name ?? string.Empty,
                PhoneNumber = phoneNumber ?? string.Empty,
                Role = role
            };
        }

        /// <summary>
        /// Retrieves the ID of the current user.
        /// </summary>
        /// <returns></returns>
        public string GetUserId() {
            var user = _httpContextAccessor.HttpContext?.User;
            return user?.FindFirst(ClaimTypes.NameIdentifier)?.Value ?? string.Empty;
        }

        /// <summary>
        /// Retrieves user information by ID.
        /// </summary>
        /// <param name="id">The ID of the user.</param>
        /// <returns>A UserInfoResponseDTO containing the user's information.</returns>
        public async Task<UserInfoResponseDTO?> GetUserById(string id)
        {
            var user = await userManager.FindByIdAsync(id);
            if (user == null)
            {
                return null; // Melhor retornar null explicitamente
            }
            return new UserInfoResponseDTO
            {
                UserId = user.Id,
                Username = user.UserName ?? string.Empty,
                Email = user.Email ?? string.Empty,
                Name = user.Name ?? string.Empty,
                PhoneNumber = user.PhoneNumber ?? string.Empty
            };
        }

    }
}
