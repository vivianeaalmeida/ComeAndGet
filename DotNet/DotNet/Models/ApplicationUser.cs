using Microsoft.AspNetCore.Identity;
using System.ComponentModel.DataAnnotations;

namespace DotNet.Models {
    public class ApplicationUser : IdentityUser {

        [Required]
        [MinLength(5)]
        public string Name { get; set; }

        [Required]
        [MinLength(9)]
        public string PhoneNumber { get; set; }

        public string Address { get; set; }
    }
}
