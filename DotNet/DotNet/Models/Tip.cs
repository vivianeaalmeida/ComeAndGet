using System.ComponentModel.DataAnnotations;

namespace DotNet.Models {
    /// <summary>
    /// Represents a tip with a title, content, and metadata about likes and favorites.
    /// </summary>
    public class Tip {
        /// <summary>
        /// Gets or sets the unique identifier for the tip.
        /// </summary>
        [Key]
        public int Id { get; set; }

        /// <summary>
        /// Gets or sets the title of the tip. 
        /// Title must be between 5 and 150 characters long.
        /// </summary>
        [Required]
        [MinLength(5)]
        [MaxLength(150)]
        public string Title { get; set; }

        /// <summary>
        /// Gets or sets the content of the tip.
        /// Content must be between 5 and 400 characters long.
        /// </summary>
        [Required]
        [MinLength(5)]
        [MaxLength(400)]
        public string Content { get; set; }

        /// <summary>
        /// Gets or sets the number of likes the tip has received.
        /// Default is 0 if not specified.
        /// </summary>
        public int? LikeCount { get; set; } = 0;

        /// <summary>
        /// Gets or sets the number of times the tip has been marked as favorite.
        /// Default is 0 if not specified.
        /// </summary>
        public int? FavoriteCount { get; set; } = 0;
    }
}
