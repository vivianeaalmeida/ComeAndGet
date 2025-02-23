using DotNet.Data;
using DotNet.DTOs;
using DotNet.Services.Interfaces;

namespace DotNet.Services
{
    public class InteractionService : IInteractionService {
        private readonly AppDbContext context;
        

        public InteractionService(AppDbContext context, UserService userService) {
            this.context = context;
           
        }

        public InteractionDTO AddInteraction(InteractionDTO interactionDTO) {
            validateContext();
            throw new NotImplementedException();
        }

        public InteractionDTO GetInteraction(int Id) {
            validateContext();
            throw new NotImplementedException();
        }

        public IEnumerable<InteractionDTO> GetInteractions() {
            validateContext();
            throw new NotImplementedException();
        }

        public void UpdateFavorite(bool favorite) {
            validateContext();
            throw new NotImplementedException();
        }

        public void UpdateLike(bool like) {
            validateContext();
            throw new NotImplementedException();
        }

        private Boolean validateContext()
        {
            if (context == null)
            {
                throw new ArgumentNullException(nameof(context));
            }
            return true;
        }
    }
}
