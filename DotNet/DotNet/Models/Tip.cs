using System.ComponentModel.DataAnnotations;

namespace DotNet.Models {
    public class Tip {
        [Key]
        public int Id { get; set; }

        [Required]
        [MinLength(5)]
        [MaxLength(150)]
        public string Title { get; set; }

        [Required]
        [MinLength(5)]
        [MaxLength(400)]
        public string Content { get; set; }

        public int? LikeCount { get; set; } = 0;

        public int? FavoriteCount { get; set; } = 0;
    }
}
