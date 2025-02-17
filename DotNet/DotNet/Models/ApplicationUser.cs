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
        /// <remarks>Minimum length is 5 characters.</remarks>
        [Required]
        [MinLength(5)]
        public string Name { get; set; }

        /// <summary>
        /// Gets or sets the user's phone number.
        /// </summary>
        /// <remarks>Minimum length is 9 characters.</remarks>
        [MinLength(9)]
        public string PhoneNumber { get; set; }
    }
}
