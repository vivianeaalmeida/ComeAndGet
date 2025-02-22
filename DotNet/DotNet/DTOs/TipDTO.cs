using System.ComponentModel.DataAnnotations;

namespace DotNet.DTOs {
    public class TipDTO {
        public int Id { get; set; }

        public string Title { get; set; }

        public string Content { get; set; }

        public int? LikeCount { get; set; }

        public int? FavoriteCount { get; set; }
    }
}
