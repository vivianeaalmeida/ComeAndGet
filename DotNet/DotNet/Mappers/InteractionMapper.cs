using DotNet.DTOs;
using DotNet.Models;

namespace DotNet.Mappers {
    public static class InteractionMapper {
        public static InteractionDTO ToDTO(Interaction interaction) {
            return new InteractionDTO {
                Id = interaction.Id,
                TipId = interaction.TipId,
                Like = interaction.Like,
                Favorite = interaction.Favorite,
                UserId = interaction.UserId
            };
        }

        public static Interaction ToEntity(InteractionDTO interactionDTO) {
            return new Interaction {
                Id = interactionDTO.Id,
                TipId = interactionDTO.TipId,
                Like = interactionDTO.Like,
                Favorite = interactionDTO.Favorite,
                UserId = interactionDTO.UserId
            };
        }
    }
}
