using DotNet.DTOs;

namespace DotNet.Services.Interfaces {
    public interface IInteractionService {
        void UpdateOrCreateInteraction(InteractionDTO interactionDTO);
    }
}