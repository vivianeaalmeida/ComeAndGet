using DotNet.Data;
using DotNet.DTOs;
using DotNet.Mappers;
using DotNet.Models;
using DotNet.Services.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace DotNet.Services
{
    public class InteractionsService : IInteractionsService {
        private readonly BlogContext context;
        

        public InteractionsService(BlogContext context, UserService userService) {
            this.context = context;
           
        }

        public void UpdateOrCreateInteraction(InteractionDTO interactionDTO) {
            var interaction = context.Interactions.SingleOrDefault(i => i.TipId == interactionDTO.TipId && i.UserId == interactionDTO.UserId );
            var interactionTemp = interaction;
            var tip = context.Tips.SingleOrDefault(t => t.Id == interactionDTO.TipId);

            if (interaction == null) {
      
                interaction = new Interaction {
                    TipId = interactionDTO.TipId,
                    UserId = interactionDTO.UserId,
                    Like = interactionDTO.Like,
                    Favorite = interactionDTO.Favorite
                };
                this.context.Interactions.Add(interaction);
                                
            }else {
                interaction.Like = interactionDTO.Like;
                interaction.Favorite = interaction.Favorite;
                this.context.Entry(interaction).State = EntityState.Modified;
            }

            if((interactionTemp != null && interactionDTO.Like != interactionTemp.Like) || interactionTemp == null) {
                if(interactionTemp != null && interactionDTO.Like == false) {
                    tip.LikeCount--;
                }else if (interactionDTO.Like == true ){
                    tip.LikeCount++;
                }
            }

            if ((interactionTemp != null && interactionDTO.Favorite != interactionTemp.Favorite) || interactionTemp == null) {
                if (interactionTemp != null && interactionDTO.Favorite == false) {
                    tip.FavoriteCount--;
                }
                else if (interactionDTO.Like == true) {
                    tip.FavoriteCount++;
                }
            }

            this.context.SaveChanges();
        }
    }
}
