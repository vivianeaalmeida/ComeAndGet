namespace DotNet.DTOs {
    /// <summary>
    /// Represents a response to a login request, containing a token and a role.
    /// </summary>
    public class LoginResponse {
        /// <summary>
        /// Gets or sets the authentication token.
        /// </summary>
        public string Token { get; set; } = string.Empty;

        /// <summary>
        /// Gets or sets the user's role.
        /// </summary>
        public string Roles { get; set; } = string.Empty;
    }
}
