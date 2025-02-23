using DotNet.Data;
using DotNet.DTOs;
using DotNet.Mappers;
using DotNet.Models;
using DotNet.Services.Interfaces;
using Microsoft.EntityFrameworkCore;
using DotNet.Exceptions;

namespace DotNet.Services {
    public class TipService : ITipService
    {
        private readonly BlogContext context;
        private readonly IInteractionService interactionService;

        public TipService(BlogContext context, IInteractionService interactionService)
        {
            this.context = context;
            this.interactionService = interactionService;
        }

        public TipDTO AddTip(TipDTO tipDTO) {
            ValidateContext();
            ValidateTip(tipDTO);

            Tip tip = TipMapper.ToEntity(tipDTO);
            this.context.Tips.Add(tip);
            this.context.SaveChanges();

            return TipMapper.ToDTO(tip);
        }

        public async Task<IEnumerable<TipDTO>> GetFavoritedTipsAsync(string userId) {
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

        public TipDTO GetTipById(int Id) {
            ValidateContext();

            var tip = GetTip(Id);

            return TipMapper.ToDTO(tip);
        }

        public IEnumerable<TipDTO> GetTips() {
            ValidateContext();

            return context.Tips
                        .OrderByDescending(tip => tip.LikeCount)     
                        .ThenByDescending(tip => tip.FavoriteCount) 
                        .Select(tip => TipMapper.ToDTO(tip))
                        .ToList();
        }

        public TipDTO UpdateTip(int Id, TipDTO tipDTO)
        {
            ValidateContext();

            var tip = GetTip(Id);
            ValidateTip(tipDTO);

            return TipMapper.ToDTO(tip);
        }

        public TipDTO RemoveTip(int Id) {
            ValidateContext();

            var tip = GetTip(Id);

            this.context.Tips.Remove(tip);
            this.context.SaveChanges();
            return TipMapper.ToDTO(tip);
        }

        private Boolean ValidateTip(TipDTO tipDTO)
        {
            if (tipDTO == null)
            {
                throw new ArgumentNullException("Tip cannot be null");
            }

            if (tipDTO.Title.Length < 5 || tipDTO.Title.Length > 100)
            {
                throw new TipValidationException("Title must be between 5 and 100 characters");
            }


            if (tipDTO.Content.Length < 5 || tipDTO.Content.Length > 200)
            {
                throw new TipValidationException("Content must be between 5 and 250 characters");
            }

            return true;
        }

        
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