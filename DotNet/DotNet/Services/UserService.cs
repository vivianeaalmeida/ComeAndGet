using System.Linq;
using System.Security.Claims;
using DotNet.DTOs;
using Microsoft.AspNetCore.Http;

namespace DotNet.Services {
    /// <summary>
    /// Provides methods for managing user-related operations.
    /// </summary>
    public class UserService {
        private readonly IHttpContextAccessor _httpContextAccessor;


        /// <summary>
        /// Initializes a new instance of the <see cref="UserService"/> class.
        /// </summary>
        /// <param name="httpContextAccessor">The IHttpContextAccessor instance.</param>
        public UserService(IHttpContextAccessor httpContextAccessor) {
            _httpContextAccessor = httpContextAccessor;
        }


        /// <summary>
        /// Retrieves information about the current user.
        /// </summary>
        /// <returns>A UserInfoResponseDTO containing the user's information.</returns>
        public UserInfoResponseDTO GetUserInfo() {
            var user = _httpContextAccessor.HttpContext?.User;

            var userName = user?.Identity?.Name;
            var email = user?.FindFirst(ClaimTypes.Email)?.Value;
            var name = user?.FindFirst(ClaimTypes.Name)?.Value;
            var phoneNumber = user?.FindFirst(ClaimTypes.MobilePhone)?.Value;
            var role = user?.Claims.FirstOrDefault(c => c.Type == ClaimTypes.Role)?.Value ?? "User";

            return new UserInfoResponseDTO {
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
    }
}
