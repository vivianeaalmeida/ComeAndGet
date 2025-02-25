using DotNet.Data;
using DotNet.DTOs;
using DotNet.Exceptions;
using DotNet.Mappers;
using DotNet.Models;
using DotNet.Services.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace DotNet.Services {
    /// <summary>
    /// Service responsible for handling user interactions with tips.
    /// </summary>
    public class InteractionService : IInteractionService {
        private readonly BlogContext context;
        private readonly UserContext usersContext;
        private readonly UserService usersService;


        /// <summary>
        /// Initializes a new instance of the <see cref="InteractionService"/> class.
        /// </summary>
        /// <param name="context">The database context.</param>
        public InteractionService(BlogContext context, UserService usersService, UserContext usersContext) {
            this.context = context;
            this.usersContext = usersContext;
            this.usersService = usersService;
        }

        /// <summary>
        /// Creates a new interaction (like or favorite) for a given tip.
        /// </summary>
        /// <param name="interactionDTO">The interaction details.</param>
        /// <returns>The created interaction.</returns>
        public InteractionDTO CreateInteraction(InteractionDTO interactionDTO) {
            ValidateContext();

            if (interactionDTO == null)
            {
                throw new ArgumentNullException(nameof(interactionDTO));
            }
            DoesUserExist(interactionDTO.UserId);

            var tip = context.Tips.SingleOrDefault(t => t.Id == interactionDTO.TipId)
                      ?? throw new TipNotFoundException("Tip not found.");

            var interaction = InteractionMapper.ToEntity(interactionDTO);

            if (interactionDTO.Like == true) {
                tip.LikeCount = (tip.LikeCount ?? 0) + 1;
            }

            if (interactionDTO.Favorite == true) {
                tip.FavoriteCount = (tip.FavoriteCount ?? 0) + 1;
            }

            context.Interactions.Add(interaction);
            context.SaveChanges();

            return InteractionMapper.ToDTO(interaction);
        }

        /// <summary>
        /// Updates an existing interaction (like or favorite).
        /// </summary>
        /// <param name="interactionId">The interaction ID.</param>
        /// <param name="interactionDTO">The updated interaction details.</param>
        /// <returns>The updated interaction.</returns>
        public InteractionDTO UpdateInteraction(int interactionId, InteractionDTO interactionDTO) {
            ValidateContext();

            if (interactionDTO == null) {
                throw new ArgumentNullException("The json body cannot be null.");
            }
            DoesUserExist(interactionDTO.UserId);
            VerifyUserOwnership(interactionDTO.UserId);

            var interaction = context.Interactions
                .Include(i => i.Tip)
                .SingleOrDefault(i => i.Id == interactionId)
                ?? throw new InteractionNotFoundException("Interaction not found.");

            var tip = interaction.Tip ?? throw new TipNotFoundException("Tip not found.");

            // Track changes to Like and Favorite
            bool likeChanged = interaction.Like != interactionDTO.Like;
            bool favoriteChanged = interaction.Favorite != interactionDTO.Favorite;

            // Update interaction properties
            interaction.Like = interactionDTO.Like;
            interaction.Favorite = interactionDTO.Favorite;

            // Update Tip counters if necessary
            if (likeChanged) {
                tip.LikeCount = (tip.LikeCount ?? 0) + (interactionDTO.Like ?? false ? 1 : -1);
            }

            if (favoriteChanged) {
                tip.FavoriteCount = (tip.FavoriteCount ?? 0) + (interactionDTO.Favorite ?? false ? 1 : -1);
            }

            context.SaveChanges();
            return InteractionMapper.ToDTO(interaction);
        }

        /// <summary>
        /// Validates that the database context is not null.
        /// </summary>
        /// <returns>True if the context is valid, otherwise throws an exception.</returns>
        private Boolean ValidateContext() {
            if (context == null) {
                throw new ArgumentNullException(nameof(context));
            }
            return true;
        }

        /// <summary>
        /// Retrieves all interactions for a specific user.
        /// </summary>
        /// <param name="userId">The ID of the user whose interactions are to be retrieved.</param>
        /// <returns>A collection of InteractionDTO objects representing the user's interactions.</returns>
        public IEnumerable<InteractionDTO> GetUserInteractions(string userId) {
            DoesUserExist(userId);
            VerifyUserOwnership(userId);

            var interactions = context.Interactions
             .Where(i => i.UserId == userId)
             .Select(interaction => InteractionMapper.ToDTO(interaction))
             .ToList();

            if (!interactions.Any()) {
                return new List<InteractionDTO>(); // Retorna lista vazia ao invés de 404
            }

            return interactions;
        }


        private bool DoesUserExist(string userId) {
            var user = usersContext.Users.Any(u => u.Id == userId);
            if (!user) {
                throw new UserNotFoundException("User not found");
            }
            return true;
        }

        private bool VerifyUserOwnership(string userId) {
            string loggedUserId = usersService.GetUserId();

            if (userId != loggedUserId) {
                throw new InteractionInvalidActionException("You don't have permission to access this resource.");
            }
            return true;
        }
    }
}