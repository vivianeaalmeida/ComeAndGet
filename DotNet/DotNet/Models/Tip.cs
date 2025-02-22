using System.ComponentModel.DataAnnotations;

namespace DotNet.Models {
    public class Tip {
        [Key]
        public int Id { get; set; }

        [Required]
        [MaxLength(100)]
        public string Title { get; set; }

        [Required]
        [MaxLength(500)]
        public string Content { get; set; }

        public int? LikeCount { get; set; }

        public int? FavoriteCount { get; set; }
    }
}
