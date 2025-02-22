using DotNet.DTOs;

namespace DotNet.Services.Interfaces {
    public interface IInteractionsService {

        IEnumerable<InteractionDTO> GetInteractions();

        InteractionDTO GetInteraction(int id);

        InteractionDTO AddInteraction(InteractionDTO interactionDTO);


        void UpdateLike(Boolean like);

        void UpdateFavorite(Boolean favorite);
    }
}
