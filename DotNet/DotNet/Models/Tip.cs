using System.ComponentModel.DataAnnotations;

namespace DotNet.Models {
    public class Tip {
        [Key]
        public int Id { get; set; }

        [Required]
        [MinLength(5)]
        [MaxLength(100)]
        public string Title { get; set; }

        [Required]
        [MinLength(5)]
        [MaxLength(250)]
        public string Content { get; set; }

        public int? LikeCount { get; set; }

        public int? FavoriteCount { get; set; }
    }
}
