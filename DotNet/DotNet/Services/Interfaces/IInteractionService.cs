using DotNet.DTOs;

namespace DotNet.Services.Interfaces {
    public interface IInteractionService {
        InteractionDTO CreateInteraction(InteractionDTO interactionDTO);

        InteractionDTO UpdateInteraction(int interactionId, InteractionDTO interactionDTO);
    }
}