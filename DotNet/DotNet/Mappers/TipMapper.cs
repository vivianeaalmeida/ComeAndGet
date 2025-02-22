using DotNet.DTOs;
using DotNet.Models;

namespace DotNet.Mappers {
    public static class TipMapper {
        public static TipDTO ToDTO(Tip tip) {
            return new TipDTO {
                Id = tip.Id,
                Title = tip.Title,
                Content = tip.Content,
                Like = tip.Like,
                Favorite = tip.Favorite
            };
        }

        public static Tip ToEntity(TipDTO tipDTO) {
            return new Tip {
                Id = tipDTO.Id,
                Title = tipDTO.Title,
                Content = tipDTO.Content,
                Like = tipDTO.Like,
                Favorite = tipDTO.Favorite
            };
        }
    }
}
