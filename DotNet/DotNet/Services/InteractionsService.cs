using DotNet.Data;
using DotNet.DTOs;
using DotNet.Services.Interfaces;

namespace DotNet.Services
{
    public class InteractionsService : IInteractionsService {
        private readonly BlogContext context;
        

        public InteractionsService(BlogContext context, UserService userService) {
            this.context = context;
           
        }

        public InteractionDTO AddInteraction(InteractionDTO interactionDTO) {
            throw new NotImplementedException();
        }

        public InteractionDTO GetInteraction(int Id) {
            throw new NotImplementedException();
        }

        public IEnumerable<InteractionDTO> GetInteractions() {
            throw new NotImplementedException();
        }

        public void UpdateFavorite(bool favorite) {
            throw new NotImplementedException();
        }

        public void UpdateLike(bool like) {
            throw new NotImplementedException();
        }
    }
}
