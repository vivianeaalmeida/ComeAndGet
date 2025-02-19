using System.Linq;
using System.Security.Claims;
using DotNet.DTOs;
using Microsoft.AspNetCore.Http;

namespace DotNet.Services {
    /// <summary>
    /// Provides methods for managing admin-related operations.
    /// </summary>
    public class AdminService {
        private readonly IHttpContextAccessor _httpContextAccessor;

        /// <summary>
        /// Initializes a new instance of the <see cref="AdminService"/> class.
        /// </summary>
        /// <param name="httpContextAccessor">The IHttpContextAccessor instance.</param>
        public AdminService(IHttpContextAccessor httpContextAccessor) {
            _httpContextAccessor = httpContextAccessor;
        }


        /// <summary>
        /// Retrieves information about the current admin user.
        /// </summary>
        /// <returns>A UserInfoResponseDTO object containing the admin user's information.</returns>
        public UserInfoResponseDTO GetAdminInfo() {
            var user = _httpContextAccessor.HttpContext?.User;

            var userName = user?.Identity?.Name;
            var email = user?.FindFirst(ClaimTypes.Email)?.Value;
            var name = user?.FindFirst(ClaimTypes.Name)?.Value;
            var phoneNumber = user?.FindFirst(ClaimTypes.MobilePhone)?.Value;
            var role = user?.Claims.FirstOrDefault(c => c.Type == ClaimTypes.Role)?.Value ?? "Admin";

            return new UserInfoResponseDTO {
                Username = userName ?? string.Empty,
                Email = email ?? string.Empty,
                Name = name ?? string.Empty,
                PhoneNumber = phoneNumber ?? string.Empty,
                Role = role
            };
        }
        /// <summary>
        /// Retrieves the ID of the current admin user.
        /// </summary>
        /// <returns></returns>
        public string GetAdminId() {
            var user = _httpContextAccessor.HttpContext?.User;
            return user?.FindFirst(ClaimTypes.NameIdentifier)?.Value ?? string.Empty;
        }
    }
}
