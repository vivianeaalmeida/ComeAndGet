using System.ComponentModel.DataAnnotations;

namespace DotNet.Models {
    public class User {

        [Required]
        [MinLength(5)]
        public string Name { get; set; }

        [Key]
        public string Email { get; set; }

        [Required]
        [MinLength(9)]
        public string PhoneNumber { get; set; }

        [Required]
        public string Role { get; set; }
    }
}
