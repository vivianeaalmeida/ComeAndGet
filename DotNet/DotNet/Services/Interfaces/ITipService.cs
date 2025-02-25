using DotNet.DTOs;

namespace DotNet.Services.Interfaces
{
    /// <summary>
    /// Defines the operations related to tips management.
    /// </summary>
    public interface ITipService
    {
        /// <summary>
        /// Retrieves all available tips.
        /// </summary>
        /// <returns>An enumerable collection of TipDTO objects.</returns>
        IEnumerable<TipDTO> GetTips();

        /// <summary>
        /// Retrieves the list of tips that a user has marked as favorites.
        /// </summary>
        /// <param name="userId">The ID of the user whose favorited tips are to be retrieved.</param>
        /// <returns>A task that represents the asynchronous operation, containing an enumerable collection of TipDTO objects.</returns>
        Task<IEnumerable<TipDTO>> GetFavoritedTipsAsync(string userId);

        /// <summary>
        /// Retrieves a specific tip by its unique identifier.
        /// </summary>
        /// <param name="id">The unique identifier of the tip to retrieve.</param>
        /// <returns>The TipDTO object corresponding to the provided id.</returns>
        TipDTO GetTipById(int id);

        /// <summary>
        /// Adds a new tip.
        /// </summary>
        /// <param name="tipDTO">The TipDTO object containing the data of the tip to add.</param>
        /// <returns>The added TipDTO object.</returns>
        TipDTO AddTip(TipDTO tipDTO);

        /// <summary>
        /// Updates an existing tip with new data.
        /// </summary>
        /// <param name="id">The unique identifier of the tip to update.</param>
        /// <param name="tipDTO">The TipDTO object containing the updated data of the tip.</param>
        /// <returns>The updated TipDTO object.</returns>
        TipDTO UpdateTip(int id, TipUpdateDTO tipDTO);

        /// <summary>
        /// Removes a tip by its unique identifier.
        /// </summary>
        /// <param name="id">The unique identifier of the tip to remove.</param>
        /// <returns>The removed TipDTO object.</returns>
        TipDTO RemoveTip(int id);
    }
}
