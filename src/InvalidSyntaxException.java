public class InvalidSyntaxException extends Exception
{
    public InvalidSyntaxException()
    {
        super("Invalid Syntax detected");
    }

    public InvalidSyntaxException(String str)
    {
        super(str);
    }

    public InvalidSyntaxException(int line)
    {
        super("Invalid Syntax detected at line: " + line);
    }
}

