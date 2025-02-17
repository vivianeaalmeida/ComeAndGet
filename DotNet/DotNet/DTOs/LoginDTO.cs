namespace DotNet.DTOs {
    /// <summary>
    /// Represents a login entity with email and password.
    /// </summary>
    public class Login {
        /// <summary>
        /// Gets or sets the email address of the user.
        /// </summary>
        public string Email { get; set; } = string.Empty;

        /// <summary>
        /// Gets or sets the password of the user.
        /// </summary>
        public string Password { get; set; } = string.Empty;
    }
}
