using DotNet.Models;
using System.ComponentModel.DataAnnotations;

namespace DotNet.DTOs {
    public class InteractionDTO {
        public int Id { get; set; }

        public int TipId { get; set; }

        public Boolean? Like { get; set; } = false;

        public Boolean? Favorite { get; set; } = false;

        public string UserId { get; set; }
    }
}
