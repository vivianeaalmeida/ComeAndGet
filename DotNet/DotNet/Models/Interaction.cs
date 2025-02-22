using System.ComponentModel.DataAnnotations;

namespace DotNet.Models {
    public class Interaction {
        [Key]
        public int Id { get; set; }

        public int TipId { get; set; }

        public Tip? Tip { get; set; }

        public Boolean? Like { get; set; } = false;

        public Boolean? Favorite { get; set; } = false;

        public string UserId { get; set; }

    }
}
