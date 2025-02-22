using DotNet.Data;
using DotNet.DTOs;
using DotNet.Mappers;
using DotNet.Models;
using DotNet.Services.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace DotNet.Services {
    public class TipsService : ITipsService {
        private readonly BlogContext context;
        private readonly InteractionsService interactionsService;
        
        public TipsService(BlogContext context, InteractionsService interactionsService) {
            this.context = context;
            this.interactionsService = interactionsService;
        }

        public TipDTO AddTip(TipDTO tipDTO) {
            Tip tip = TipMapper.ToEntity(tipDTO);
            this.context.Tips.Add(tip);
            this.context.SaveChanges();

            return TipMapper.ToDTO(tip);            
        }

        public async Task<IEnumerable<TipDTO>> GetFavoritedTipsAsync(string userId) {
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
            var tip = this.context.Tips.FirstOrDefault(t => t.Id == Id);
            if (tip == null) {
                throw new ArgumentNullException("Tip not found");
            }

            return TipMapper.ToDTO(tip);
        }

        public IEnumerable<TipDTO> GetTips() {
            return context.Tips.Select(tip => TipMapper.ToDTO(tip)).ToList();
        }

        public TipDTO RemoveTip(int Id) {
            var tip = context.Tips.SingleOrDefault(t => t.Id == Id);
            if (tip == null) {
                throw new ArgumentNullException("Tip not found");
            }

            this.context.Tips.Remove(tip);
            this.context.SaveChanges();
            return TipMapper.ToDTO(tip);
        }

        public TipDTO UpdateTip(int Id, TipDTO tipDTO) {
            throw new NotImplementedException();
        }
    }
}
