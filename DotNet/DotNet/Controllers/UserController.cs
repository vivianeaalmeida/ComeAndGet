﻿using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace DotNet.Controllers {
    [Authorize(Roles = "ApplicationUser")]
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase {

        [HttpGet]
        public IActionResult Get() {
            return Ok("You have accessed the ApplicationUser area.");
        }
    }
}
