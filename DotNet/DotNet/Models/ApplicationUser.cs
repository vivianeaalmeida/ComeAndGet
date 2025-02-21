using Microsoft.AspNetCore.Identity;
using System.ComponentModel.DataAnnotations;

namespace DotNet.Models {
    /// <summary>
    /// Represents an application user with additional properties.
    /// </summary>
    public class ApplicationUser : IdentityUser {

        /// <summary>
        /// Gets or sets the user's name.
        /// </summary>
        /// <remarks>
        /// The name must be between 5 and 50 characters in length.
        /// </remarks>
        [Required]
        [MinLength(5)]
        [MaxLength(50)]
        public string Name { get; set; }

        /// <summary>
        /// Gets or sets the user's phone number.
        /// </summary>
        /// <remarks>
        /// The phone number must be exactly 9 characters in length.
        /// </remarks>
        [MinLength(9)]
        [MaxLength(9)]
        public string PhoneNumber { get; set; }
    }
}
