namespace DotNet.DTOs {
    /// <summary>
    /// Represents a user information response entity with username, email, name, phone number, and role.
    /// </summary>
    public class UserInfoResponseDTO {
        /// <summary>
        /// Gets or sets the username.
        /// </summary>
        public string Username { get; set; } = string.Empty;

        /// <summary>
        /// Gets or sets the email address.
        /// </summary>
        public string Email { get; set; } = string.Empty;

        /// <summary>
        /// Gets or sets the full name.
        /// </summary>
        public string Name { get; set; } = string.Empty;

        /// <summary>
        /// Gets or sets the phone number.
        /// </summary>
        public string PhoneNumber { get; set; } = string.Empty;

        /// <summary>
        /// Gets or sets the user role (defaults to "User").
        /// </summary>
        public string Role { get; set; } = "User";
    }
}
