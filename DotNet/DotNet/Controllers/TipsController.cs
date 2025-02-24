using DotNet.DTOs;
using DotNet.Exceptions;
using DotNet.Services;
using DotNet.Services.Interfaces;
using Microsoft.AspNetCore.Mvc;
using Microsoft.CodeAnalysis.CSharp.Syntax;

namespace DotNet.Controllers {
    /// <summary>
    /// Handles tips-related operations.
    /// </summary>
    [Route("api/v1/[controller]")]
    [ApiController]
    public class TipsController : Controller {
        private readonly ITipService tipService;

        /// <summary>
        /// Initializes a new instance of the <see cref="TipsController"/> class.
        /// </summary>
        /// <param name="tipService">The service for handling tip operations.</param>
        public TipsController(ITipService tipService) {
            this.tipService = tipService;
        }

        /// <summary>
        /// Retrieves all tips.
        /// </summary>
        /// <returns>A list of all tips.</returns>
        [HttpGet]
        public ActionResult<IEnumerable<TipDTO>> GetAll() {
            try {
                var tips = tipService.GetTips();
                return Ok(tips);
            }
            catch (Exception e)
            {
                return BadRequest(new { message = $"An unexpected error occurred - {e.Message}." });
            }
        }

        /// <summary>
        /// Retrieves a specific tip by ID.
        /// </summary>
        /// <param name="id">The ID of the tip.</param>
        /// <returns>The requested tip if found; otherwise, a NotFound response.</returns>
        [HttpGet("{id}")]
        public ActionResult<TipDTO> GetById(int id) {
            try {
                var tip = tipService.GetTipById(id);
                return Ok(tip);
            }
            catch (TipNotFoundException e) 
            {
                return NotFound(new { message = e.Message });
            }
            catch (Exception e)
            {
                return BadRequest(new { message = $"An unexpected error occurred - {e.Message}." });
            }
        }

        /// <summary>
        /// Retrieves the favorite tips of a specific user.
        /// </summary>
        /// <param name="userId">The ID of the user.</param>
        /// <returns>A list of favorited tips.</returns>
        [HttpGet("favorites/users/{userId}")]
        public async Task<ActionResult<IEnumerable<TipDTO>>> GetFavoritedTips(string userId) {
            try 
            {
                var favoriteTips = await tipService.GetFavoritedTipsAsync(userId);
                return Ok(favoriteTips);
            }
            catch (Exception e)
            {
                return BadRequest(new { message = $"An unexpected error occurred - {e.Message}." });
            }
        }

        /// <summary>
        /// Adds a new tip.
        /// </summary>
        /// <param name="tipDTO">The tip data to add.</param>
        /// <returns>The created tip with its generated ID.</returns>
        [HttpPost]
        public ActionResult<TipDTO> Add([FromBody] TipDTO tipDTO)
        {
            try
            {
                var createdTip = tipService.AddTip(tipDTO);
                return CreatedAtAction(nameof(Add), new { id = createdTip.Id }, createdTip);
            }
            catch (TipValidationException e)
            {
                return BadRequest(new { message = e.Message });
            }
            catch (ArgumentNullException e)
            {
                return BadRequest(new { message = e.Message });
            }
            catch (Exception e)
            {
                return BadRequest(new { message = $"An unexpected error occurred - {e.Message}." });
            }
        }

        /// <summary>
        /// Updates an existing tip.
        /// </summary>
        /// <param name="id">The ID of the tip to update.</param>
        /// <param name="tipDTO">The updated tip data.</param>
        /// <returns>The updated tip or an error message if the update fails.</returns>
        [HttpPut("{id}")]
        public ActionResult Update(int id, [FromBody] TipDTO tipDTO) {
            if (id != tipDTO.Id)
            {
                return BadRequest("The provided ids do not match.");
            }

            try 
            {
                tipService.UpdateTip(id, tipDTO);
                return Ok(tipDTO);
            }
            catch (TipNotFoundException e) 
            {
                return NotFound(new { message = e.Message });
            }
            catch (TipValidationException e) 
            {
                return BadRequest(new { message = e.Message });
            }
            catch (ArgumentNullException e)
            {
                return BadRequest(new { message = e.Message });
            }
            catch (Exception e) {
                return BadRequest(new { message = $"An unexpected error occurred - {e.Message}." });
            }
        }

        /// <summary>
        /// Deletes a tip by ID.
        /// </summary>
        /// <param name="id">The ID of the tip to remove.</param>
        /// <returns>The removed tip if successful, or an error message if not found.</returns>
        [HttpDelete("{id}")]
        public ActionResult Remove(int id) {
            try 
            {
                var removedTip = tipService.RemoveTip(id);
                return Ok(removedTip);
            }
            catch (TipNotFoundException e) 
            {
                return NotFound(new { message = e.Message });
            }
            catch (Exception e) 
            {
                return BadRequest(new { message = $"An unexpected error occurred - {e.Message}." });
            }
        }
    }
}
