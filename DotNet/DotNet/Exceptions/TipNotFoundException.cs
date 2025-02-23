namespace DotNet.Exceptions
{
    public class TipNotFoundException : Exception
    {
        public TipNotFoundException(string message): base(message) { }
    }
}