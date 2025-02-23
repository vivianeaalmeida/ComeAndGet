using DotNet.DTOs;

namespace DotNet.Services.Interfaces {
    public interface ITipService {
        IEnumerable<TipDTO> GetTips();

        Task<IEnumerable<TipDTO>> GetFavoritedTipsAsync(string userId);

        TipDTO GetTipById(int id);

        TipDTO AddTip(TipDTO tipDTO);

        TipDTO RemoveTip(int id);

        TipDTO UpdateTip(int id, TipDTO tipDTO);
    }
}
