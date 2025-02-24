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
    public class AdminsController : ControllerBase {
        private readonly AdminService adminService;

        /// <summary>
        /// Initializes a new instance of the <see cref="AdminsController"/> class.
        /// </summary>
        /// <param name="adminService">The instance of AdminService.</param>
        public AdminsController(AdminService adminService) {
            this.adminService = adminService;
        }

        /// <summary>
        /// Retrieves admin information.
        /// </summary>
        [HttpGet]
        public IActionResult Get() {
            var adminInfo = adminService.GetAdminInfo();
            return Ok(adminInfo);
        }


        /// <summary>
        /// Retrieves the admin ID.
        /// </summary>
        /// <returns></returns>
        [HttpGet("token")]
        public IActionResult GetAdminId() {
            var id = adminService.GetAdminId();
            return Ok(id);
        }
    }
}
