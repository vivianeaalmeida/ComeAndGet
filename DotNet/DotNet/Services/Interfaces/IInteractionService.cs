using DotNet.DTOs;

namespace DotNet.Services.Interfaces
{
    /// <summary>
    /// Defines the operations related to interaction management.
    /// </summary>
    public interface IInteractionService
    {
        /// <summary>
        /// Creates a new interaction.
        /// </summary>
        /// <param name="interactionDTO">The InteractionDTO object containing the data of the interaction to create.</param>
        /// <returns>The created InteractionDTO object.</returns>
        InteractionDTO CreateInteraction(InteractionDTO interactionDTO);

        /// <summary>
        /// Updates an existing interaction with new data.
        /// </summary>
        /// <param name="interactionId">The unique identifier of the interaction to update.</param>
        /// <param name="interactionDTO">The InteractionDTO object containing the updated data of the interaction.</param>
        /// <returns>The updated InteractionDTO object.</returns>
        InteractionDTO UpdateInteraction(int interactionId, InteractionDTO interactionDTO);
    }
}