using DotNet.DTOs;

namespace DotNet.Services.Interfaces {
    public interface IInteractionsService {
        void UpdateOrCreateInteraction(InteractionDTO interactionDTO);
    }
}
