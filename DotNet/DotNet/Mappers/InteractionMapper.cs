using DotNet.DTOs;
using DotNet.Models;

namespace DotNet.Mappers {
    /// <summary>
    /// Provides methods to map between Interaction entities and InteractionDTOs.
    /// </summary>
    public static class InteractionMapper {

        /// <summary>
        /// Maps an Interaction entity to an InteractionDTO.
        /// </summary>
        /// <param name="interaction">The Interaction entity to map.</param>
        /// <returns>An InteractionDTO object representing the provided Interaction entity.</returns>
        public static InteractionDTO ToDTO(Interaction interaction) {
            return new InteractionDTO {
                Id = interaction.Id,
                TipId = interaction.TipId,
                Like = interaction.Like,
                Favorite = interaction.Favorite,
                UserId = interaction.UserId
            };
        }

        /// <summary>
        /// Maps an InteractionDTO to an Interaction entity.
        /// </summary>
        /// <param name="interactionDTO">The InteractionDTO to map.</param>
        /// <returns>An Interaction entity representing the provided InteractionDTO.</returns>
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
