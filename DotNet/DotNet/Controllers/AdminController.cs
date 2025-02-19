using DotNet.DTOs;
using DotNet.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace DotNet.Controllers {
    /// <summary>
    /// Controller for handling admin-related operations.
    /// </summary>
    [Authorize(Roles = "Admin")]
    [Route("api/v1/[controller]")]
    [ApiController]
    public class AdminController : ControllerBase {
        private readonly AdminService _adminService;

        /// <summary>
        /// Initializes a new instance of the <see cref="AdminController"/> class.
        /// </summary>
        /// <param name="adminService">The instance of AdminService.</param>
        public AdminController(AdminService adminService) {
            _adminService = adminService;
        }

        /// <summary>
        /// Retrieves admin information.
        /// </summary>
        [HttpGet]
        public IActionResult Get() {
            var adminInfo = _adminService.GetAdminInfo();
            return Ok(adminInfo);
        }


        /// <summary>
        /// Retrieves the admin ID.
        /// </summary>
        /// <returns></returns>
        [HttpGet("token")]
        public IActionResult GetAdminId() {
            var id = _adminService.GetAdminId();
            return Ok(id);
        }
    }
}
