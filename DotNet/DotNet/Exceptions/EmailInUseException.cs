namespace DotNet.Exceptions {
    public class EmailInUseException : Exception {
        public EmailInUseException(string message) : base(message) { }
    }
}
