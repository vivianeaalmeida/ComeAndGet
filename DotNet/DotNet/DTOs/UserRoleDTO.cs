namespace DotNet.DTOs {
    /// <summary>
    /// Represents a user role data transfer object.
    /// </summary>
    public class UserRoleDTO {
        /// <summary>
        /// Gets or sets the username.
        /// </summary>
        public string Username { get; set; } = string.Empty;

        /// <summary>
        /// Gets or sets the role.
        /// </summary>
        public string Role { get; set; } = string.Empty;
    }
}
