using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace DotNet.DTOs {
    public class TipDTO {
        public int Id { get; set; }

        public string Title { get; set; }

        public string Content { get; set; }

        [JsonIgnore]
        public int? LikeCount { get; set; }

        [JsonIgnore]
        public int? FavoriteCount { get; set; }
    }
}
