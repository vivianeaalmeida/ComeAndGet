using DotNet.DTOs;
using DotNet.Models;

namespace DotNet.Mappers {
    /// <summary>
    /// Provides methods to map between Tip entities and TipDTOs.
    /// </summary>
    public static class TipMapper {

        /// <summary>
        /// Maps a Tip entity to a TipDTO.
        /// </summary>
        /// <param name="tip">The Tip entity to map.</param>
        /// <returns>A TipDTO object representing the provided Tip entity.</returns>
        public static TipDTO ToDTO(Tip tip) {
            return new TipDTO {
                Id = tip.Id,
                Title = tip.Title,
                Content = tip.Content,
                LikeCount = tip.LikeCount,
                FavoriteCount = tip.FavoriteCount
            };
        }

        /// <summary>
        /// Maps a TipDTO to a Tip entity.
        /// </summary>
        /// <param name="tipDTO">The TipDTO to map.</param>
        /// <returns>A Tip entity representing the provided TipDTO.</returns>
        public static Tip ToEntity(TipDTO tipDTO) {
            return new Tip {
                Id = tipDTO.Id,
                Title = tipDTO.Title,
                Content = tipDTO.Content,
                LikeCount = tipDTO.LikeCount,
                FavoriteCount = tipDTO.FavoriteCount
            };
        }
    }
}