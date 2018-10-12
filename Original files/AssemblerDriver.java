
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssemblerDriver
{

    static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) //throws InvalidSyntaxException
    {
        ExecuteProgram();
        //AssemblerTester.test();
        // AssemblerTester.testSplit();
        //AssemblerTester.compareFiles();

    }


    public static void ExecuteProgram() //throws InvalidSyntaxException
    {
        String message;

        System.out.println("The files to assemble must be stored in \"/file\" directory");
        System.out.print("Enter Assembly(.asm) File to compile: ");
        String input = keyboard.nextLine();

        ParserModule parseModule  = null;

        try
        {
            parseModule = new ParserModule(input);
           // parseModule.assembleFile();
            message = "Assemble another .asm file?(Y/N): ";
        }
        catch (IOException e)
        {
            message = "Unable to read from " + input + ", Try again?(Y/N): ";
        }
//        catch (InvalidSyntaxException e)
//        {
//            // message = "Would you like to Try again(Y/N): ";
//            // throw new InvalidSyntaxException(e.getMessage());
//            message = "\n!!!!!!!!!!!! " + e.getMessage() + " !!!!!!!!!!!!!!!!!\n";
//            message += "\nWould you like to Try Again?(Y/N): ";
//        }

        System.out.print(message);
        input = keyboard.nextLine();
        System.out.println();

        if (input.equalsIgnoreCase("Y"))
        {
            ExecuteProgram();
        }
        else
        {
            System.out.println("Goodbye!...");
        }
    }
}
