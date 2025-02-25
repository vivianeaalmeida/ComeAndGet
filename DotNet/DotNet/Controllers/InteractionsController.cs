using DotNet.DTOs;
using DotNet.Exceptions;
using DotNet.Services;
using DotNet.Services.Interfaces;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace DotNet.Controllers {

    /// <summary>
    /// Handles interactions-related operations.
    /// </summary>
    [Route("api/v1/[controller]")]
    [ApiController]
    public class InteractionsController : ControllerBase {
        private readonly IInteractionService interactionService;

        /// <summary>
        /// Initializes a new instance of the <see cref="InteractionController"/> class.
        /// </summary>
        /// <param name="interactionService">The service for handling interaction operations.</param>
        public InteractionsController(IInteractionService interactionService) {
            this.interactionService = interactionService;
        }

        /// <summary>
        /// Creates a new interaction.
        /// </summary>
        /// <param name="interactionDTO">The interaction data to create.</param>
        /// <returns>The created interaction with its generated ID.</returns>
        [Authorize(Roles = "User")]
        [HttpPost]
        public IActionResult CreateInteraction([FromBody] InteractionDTO interactionDTO) {
            try {
                var createdInteraction = interactionService.CreateInteraction(interactionDTO);
                return CreatedAtAction(nameof(CreateInteraction), new { id = createdInteraction.Id }, createdInteraction);
            }
            catch (TipNotFoundException ex) {
                return BadRequest(new { message = ex.Message });
            }
            catch (ArgumentNullException ex) {
                return BadRequest(new { message = ex.Message });
            }
            catch (Exception ex)
            {
                return BadRequest(new { message = $"An unexpected error occurred - {ex.Message}." });
            }
        }

        /// <summary>
        /// Updates an existing interaction.
        /// </summary>
        /// <param name="id">The ID of the interaction to update.</param>
        /// <param name="interactionDTO">The updated interaction data.</param>
        /// <returns>The updated interaction or an error message if the update fails.</returns>
        [Authorize(Roles = "User")]
        [HttpPut("{id}")]
        public IActionResult UpdateInteraction(int id, [FromBody] InteractionDTO interactionDTO) {
          
            try 
            {
                interactionService.UpdateInteraction(id, interactionDTO);
                return Ok(interactionDTO);
            }
            catch (InteractionNotFoundException ex) {
                return NotFound(new { message = ex.Message });
            }
            catch (TipNotFoundException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
            catch (ArgumentNullException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
            catch (Exception ex) {
                return BadRequest(new { message = $"An unexpected error occurred - {ex.Message}." });
            }
        }

        /// <summary>
        /// Retrieves all interactions for a specific user.
        /// </summary>
        /// <param name="userId">The ID of the user whose interactions are to be retrieved.</param>
        /// <returns>A list of interactions associated with the specified user.</returns>
        [HttpGet("users/{userId}")]
        public ActionResult<IEnumerable<InteractionDTO>> GetUserInteractions(string userId) {
            return Ok(interactionService.GetUserInteractions(userId));
        }

    }
}
