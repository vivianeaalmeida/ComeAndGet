using System.ComponentModel.DataAnnotations;

namespace DotNet.Models
{
    /// <summary>
    /// Represents an interaction between a user and a tip, which can include liking or favoriting the tip.
    /// </summary>
    public class Interaction
    {
        /// <summary>
        /// Gets or sets the unique identifier for the interaction.
        /// </summary>
        [Key]
        public int Id { get; set; }

        /// <summary>
        /// Gets or sets the associated Tip object for this interaction.
        /// This property is optional and can be null.
        /// </summary>
        public Tip? Tip { get; set; }

        /// <summary>
        /// Gets or sets the unique identifier for the associated tip.
        /// </summary>
        public int TipId { get; set; }

        /// <summary>
        /// Gets or sets the unique identifier of the user performing the interaction.
        /// </summary>
        public string UserId { get; set; }

        /// <summary>
        /// Gets or sets a value indicating whether the user liked the tip.
        /// Default is false if not specified.
        /// </summary>
        public Boolean? Like { get; set; } = false;

        /// <summary>
        /// Gets or sets a value indicating whether the user favorited the tip.
        /// Default is false if not specified.
        /// </summary>
        public Boolean? Favorite { get; set; } = false;
    }
}