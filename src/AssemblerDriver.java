
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AssemblerDriver
{

    static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) //throws InvalidSyntaxException
    {
        //ExecuteProgram();
        //System.out.println(intTo16bitBinary(65535));
        CodeModule codeModule = new CodeModule();
        codeModule.fillHashTable();
        System.out.println(codeModule);
    }

    public static void ExecuteProgram() //throws InvalidSyntaxException
    {
        String message;

        System.out.println("The files to assemble must be stored in \"/file\" directory");
        System.out.print("Enter Assembly(.asm) File to compile: ");
        String input = keyboard.nextLine();

        ParserModule parseModule = new ParserModule(input);

        try
        {
            parseModule.assembleFile();
            message = "Assemble another .asm file?(Y/N): ";
        }
        catch (IOException e)
        {
            message = "Unable to read from " + input + ", Try again?(Y/N): ";
        }
        catch(InvalidSyntaxException e)
        {
           // message = "Would you like to Try again(Y/N): ";
           // throw new InvalidSyntaxException(e.getMessage());
            message = "\n!!!!!!!!!!!! " + e.getMessage() + " !!!!!!!!!!!!!!!!!\n";
            message += "\nWould you like to Try Again?(Y/N): ";


        }

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

        //List<String> testList = readFile("TestFile.txt");
        // printList(testList);
    }


    public static String process(String line)
    {
        String temp = line.trim();
        return temp;
    }

    /***
     * Method: Prints the contents of List for testing purposes.
     * @param list The list to print contents
     */
    public static void printList(List<String> list)
    {
        try
        {
            for (String string : list)
            {
                System.out.println(string);
            }
        }
        catch (Exception e)
        {
            System.out.println("Could not print List content");
        }
    }


    /**
     * Method: Converts an Integer value into it's equivalent 16-bit binary value.
     * Range of 0 - 65,535
     *
     * @param value
     * @return
     */
    private static String intTo16bitBinary(int value)
    {
        int bitValue = 16;

        String intToBinary = Integer.toBinaryString(value);

        int zerosToAdd = bitValue - intToBinary.length();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < zerosToAdd; i++)
        {
            sb.append("0");
        }

        sb.append(intToBinary);

        return sb.toString();
    }

}
