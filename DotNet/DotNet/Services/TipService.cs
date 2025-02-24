using DotNet.Data;
using DotNet.DTOs;
using DotNet.Mappers;
using DotNet.Models;
using DotNet.Services.Interfaces;
using Microsoft.EntityFrameworkCore;
using DotNet.Exceptions;

namespace DotNet.Services {
    /// <summary>
    /// Service responsible for managing tips.
    /// </summary>
    public class TipService : ITipService
    {
        private readonly BlogContext context;
        private readonly IInteractionService interactionService;

        /// <summary>
        /// Initializes a new instance of the <see cref="TipService"/> class.
        /// </summary>
        /// <param name="context">The database context.</param>
        /// <param name="interactionService">The service for handling interactions.</param>
        public TipService(BlogContext context, IInteractionService interactionService)
        {
            this.context = context;
            this.interactionService = interactionService;
        }

        /// <summary>
        /// Retrieves all tips, ordered by likes and favorites.
        /// </summary>
        /// <returns>A list of tips.</returns>
        public IEnumerable<TipDTO> GetTips()
        {
            ValidateContext();

            return context.Tips
                        .OrderByDescending(tip => tip.LikeCount)
                        .ThenByDescending(tip => tip.FavoriteCount)
                        .Select(tip => TipMapper.ToDTO(tip))
                        .ToList();
        }

        /// <summary>
        /// Retrieves a list of favorited tips by a user.
        /// </summary>
        /// <param name="userId">The user ID.</param>
        /// <returns>A list of favorited tips.</returns>
        public async Task<IEnumerable<TipDTO>> GetFavoritedTipsAsync(string userId)
        {
            ValidateContext();

            var favoriteTipIds = await context.Interactions
                .Where(i => i.UserId == userId && i.Favorite == true)
                .Select(i => i.TipId)
                .ToListAsync();

            var favoriteTips = await context.Tips
                .Where(t => favoriteTipIds.Contains(t.Id))
                .AsNoTracking()
                .ToListAsync();

            return favoriteTips.Select(TipMapper.ToDTO);
        }

        /// <summary>
        /// Retrieves a tip by its ID.
        /// </summary>
        /// <param name="Id">The tip ID.</param>
        /// <returns>The tip with the specified ID.</returns>
        public TipDTO GetTipById(int Id)
        {
            ValidateContext();

            var tip = GetTip(Id);

            return TipMapper.ToDTO(tip);
        }

        /// <summary>
        /// Adds a new tip.
        /// </summary>
        /// <param name="tipDTO">The tip data to add.</param>
        /// <returns>The created tip.</returns>
        public TipDTO AddTip(TipDTO tipDTO) {
            ValidateContext();
            ValidateTip(tipDTO);

            Tip tip = TipMapper.ToEntity(tipDTO);
            this.context.Tips.Add(tip);
            this.context.SaveChanges();

            return TipMapper.ToDTO(tip);
        }

        /// <summary>
        /// Updates an existing tip.
        /// </summary>
        /// <param name="Id">The ID of the tip to update.</param>
        /// <param name="tipDTO">The updated tip data.</param>
        /// <returns>The updated tip.</returns>
        public TipDTO UpdateTip(int Id, TipDTO tipDTO)
        {
            ValidateContext();

            Tip tip = TipMapper.ToEntity(tipDTO);

            this.context.Update(tip);
            this.context.SaveChanges();

            return TipMapper.ToDTO(tip);
        }

        /// <summary>
        /// Removes a tip by its ID.
        /// </summary>
        /// <param name="Id">The ID of the tip to remove.</param>
        /// <returns>The removed tip.</returns>
        public TipDTO RemoveTip(int Id) {
            ValidateContext();

            var tip = GetTip(Id);

            this.context.Tips.Remove(tip);
            this.context.SaveChanges();
            return TipMapper.ToDTO(tip);
        }

        /// <summary>
        /// Validates the given tip data.
        /// </summary>
        /// <param name="tipDTO">The tip data to validate.</param>
        /// <returns>True if the tip is valid, otherwise throws an exception.</returns>
        private Boolean ValidateTip(TipDTO tipDTO)
        {
            if (tipDTO == null)
            {
                throw new ArgumentNullException("Tip cannot be null");
            }
            if (tipDTO.Title.Length < 5 || tipDTO.Title.Length > 150)
            {
                throw new TipValidationException("Title must be between 5 and 100 characters");
            }
            if (tipDTO.Content.Length < 5 || tipDTO.Content.Length > 400)
            {
                throw new TipValidationException("Content must be between 5 and 400 characters");
            }

            return true;
        }

        /// <summary>
        /// Retrieves a tip by its ID.
        /// </summary>
        /// <param name="id">The tip ID.</param>
        /// <returns>The tip entity if found, otherwise throws an exception.</returns>
        private Tip GetTip(int id)
        {
            var tip = this.context.Tips.FirstOrDefault(t => t.Id == id);
            //var tip = this.context.Tips.Find(id);

            if (tip == null)
            {
                throw new TipNotFoundException("Tip not found");
            }
            return tip;
        }

        /// <summary>
        /// Validates the database context.
        /// </summary>
        /// <returns>True if the context is valid, otherwise throws an exception.</returns>
        private Boolean ValidateContext()
        {
            if (context == null)
            {
                throw new ArgumentNullException(nameof(context));
            }
            return true;
        }
    }
}