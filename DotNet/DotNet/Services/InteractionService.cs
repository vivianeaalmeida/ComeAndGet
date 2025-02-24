using DotNet.Data;
using DotNet.DTOs;
using DotNet.Exceptions;
using DotNet.Mappers;
using DotNet.Models;
using DotNet.Services.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace DotNet.Services {
    public class InteractionService : IInteractionService {
        private readonly BlogContext context;

        public InteractionService(BlogContext context, UserService userService) {
            this.context = context;
        }

        public InteractionDTO CreateInteraction(InteractionDTO interactionDTO) {
            ValidateContext();

            if (interactionDTO == null)
            {
                throw new ArgumentNullException(nameof(interactionDTO));
            }

            var tip = context.Tips.SingleOrDefault(t => t.Id == interactionDTO.TipId)
                      ?? throw new TipNotFoundException("Tip not found.");

            var interaction = InteractionMapper.ToEntity(interactionDTO);
            //var interaction = new Interaction {
            //    TipId = interactionDTO.TipId,
            //    UserId = interactionDTO.UserId,
            //    Like = interactionDTO.Like,
            //    Favorite = interactionDTO.Favorite
            //};

            if (interactionDTO.Like == true) {
                tip.LikeCount = (tip.LikeCount ?? 0) + 1;
            }

            if (interactionDTO.Favorite == true) {
                tip.FavoriteCount = (tip.FavoriteCount ?? 0) + 1;
            }

            context.Interactions.Add(interaction);
            context.SaveChanges();

            return InteractionMapper.ToDTO(interaction);
        }

        public InteractionDTO UpdateInteraction(int interactionId, InteractionDTO interactionDTO) {
            ValidateContext();

            if (interactionDTO == null) {
                throw new ArgumentNullException("The json body cannot be null.");
            }

            var interaction = context.Interactions
                .Include(i => i.Tip)
                .SingleOrDefault(i => i.Id == interactionId)
                ?? throw new InteractionNotFoundException("Interaction not found.");

            var tip = interaction.Tip ?? throw new TipNotFoundException("Tip not found.");

            // Track changes to Like and Favorite
            bool likeChanged = interaction.Like != interactionDTO.Like;
            bool favoriteChanged = interaction.Favorite != interactionDTO.Favorite;

            // Update interaction properties
            interaction.Like = interactionDTO.Like;
            interaction.Favorite = interactionDTO.Favorite;

            // Update Tip counters if necessary
            if (likeChanged) {
                tip.LikeCount = (tip.LikeCount ?? 0) + (interactionDTO.Like ?? false ? 1 : -1);
            }

            if (favoriteChanged) {
                tip.FavoriteCount = (tip.FavoriteCount ?? 0) + (interactionDTO.Favorite ?? false ? 1 : -1);
            }

            context.SaveChanges();
            return InteractionMapper.ToDTO(interaction);
        }

        private Boolean ValidateContext() {
            if (context == null) {
                throw new ArgumentNullException(nameof(context));
            }
            return true;
        }
    }
}