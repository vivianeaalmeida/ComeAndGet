using System.Text.Json.Serialization;

namespace DotNet.DTOs {
    /// <summary>
    /// Represents a registration entity with username, email, password, name, and phone number.
    /// </summary>
    public class Register {
        /// <summary>
        /// Gets or sets the username.
        /// </summary>
        public string Username { get; set; } = string.Empty;

        /// <summary>
        /// Gets or sets the email address.
        /// </summary>
        public string Email { get; set; } = string.Empty;

        /// <summary>
        /// Gets or sets the password.
        /// </summary>
        public string Password { get; set; } = string.Empty;

        /// <summary>
        /// Gets or sets the full name.
        /// </summary>
        public string Name { get; set; } = string.Empty;

        /// <summary>
        /// Gets or sets the phone number.
        /// </summary>
        public string PhoneNumber { get; set; } = string.Empty;

        //public string Role { get; set; } = "User";
    }
}