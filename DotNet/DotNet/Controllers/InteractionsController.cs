using DotNet.DTOs;
using DotNet.Exceptions;
using DotNet.Services.Interfaces;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace DotNet.Controllers {
    [Route("api/[controller]")]
    [ApiController]
    public class InteractionController : ControllerBase {
        private readonly IInteractionService interactionService;

        public InteractionController(IInteractionService interactionService) {
            this.interactionService = interactionService;
        }

        [HttpPost]
        public IActionResult CreateInteraction([FromBody] InteractionDTO interactionDTO) {
            if (interactionDTO == null) return BadRequest("Interaction data is required.");

            try {
                interactionService.CreateInteraction(interactionDTO);
                return Ok("Interaction created successfully.");
            }
            catch (TipNotFoundException ex) {
                return NotFound(new { message = ex.Message });
            }
            catch (ArgumentNullException ex) {
                return BadRequest(new { message = "The body cannot be null." });
            }
            catch (Exception ex) {
                return StatusCode(StatusCodes.Status500InternalServerError, new { message = "An error occurred while creating the interaction." });
            }
        }

        [HttpPut("{id}")]
        public IActionResult UpdateInteraction(int id, [FromBody] InteractionDTO interactionDTO) {
            if (interactionDTO == null) return BadRequest("Interaction data is required.");
            if (id != interactionDTO.Id) return BadRequest("The provided ids do not match.");

            try {
                interactionService.UpdateInteraction(id, interactionDTO);
                return Ok("Interaction updated successfully.");
            }
            catch (TipNotFoundException ex) {
                return NotFound(new { message = ex.Message });
            }
            catch (InteractionNotFoundException ex) {
                return NotFound(new { message = ex.Message });
            }
            catch (Exception ex) {
                return StatusCode(StatusCodes.Status500InternalServerError, new { message = "An error occurred while updating the interaction." });
            }
        }
    }
}
