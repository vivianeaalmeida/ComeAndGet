using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace DotNet.DTOs
{
    /// <summary>
    /// Represents a Data Transfer Object (DTO) for a Tip, used to transfer tip data between layers.
    /// </summary>
    public class TipUpdateDTO
    {
        /// <summary>
        /// Gets or sets the unique identifier for the tip.
        /// </summary>
        public int Id { get; set; }

        /// <summary>
        /// Gets or sets the title of the tip.
        /// </summary>
        public string Title { get; set; }

        /// <summary>
        /// Gets or sets the content of the tip.
        /// </summary>
        public string Content { get; set; }
    }
}
