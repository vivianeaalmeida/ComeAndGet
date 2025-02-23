using DotNet.DTOs;
using DotNet.Exceptions;
using DotNet.Services;
using DotNet.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;
using Microsoft.CodeAnalysis.CSharp.Syntax;

namespace DotNet.Controllers {
    [Route("api/v1/[controller]")]
    [ApiController]
    public class TipsController : Controller {
        private readonly ITipService tipService;

        public TipsController(ITipService tipService) {
            this.tipService = tipService;
        }

        [HttpPut("{id}")]
        public ActionResult Update(int id, [FromBody] TipDTO tipDTO) {
            if (id != tipDTO.Id) {
                return BadRequest();
            }
            try {
                tipService.UpdateTip(id, tipDTO);
                return Ok();
            }
            catch (TipNotFoundException e) {
                return NotFound(new { message = e.Message });
            }
            catch (TipValidationException e) {
                return BadRequest(new { message = e.Message });
            }
            catch (Exception e) {
                return BadRequest(new { message = "An unexpected error occurred.", detail = e.Message });
            }
        }
    }
}
