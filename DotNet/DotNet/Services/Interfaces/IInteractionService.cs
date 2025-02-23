using DotNet.DTOs;

namespace DotNet.Services.Interfaces {
    public interface IInteractionService {
        void CreateInteraction(InteractionDTO interactionDTO);

        void UpdateInteraction(int interactionId, InteractionDTO interactionDTO);
    }
}