﻿using DotNet.DTOs;
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
