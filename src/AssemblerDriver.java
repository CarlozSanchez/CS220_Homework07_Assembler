import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssemblerDriver
{
    static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args)
    {
       // ExecuteProgram();
       System.out.println(intTo16bitBinary(65535));

    }

    /**
     * Method: Converts an Integer value into it's equivalent 16-bit binary value.
     *         Range of 0 - 65,535
     * @param value
     * @return
     */
    private static String intTo16bitBinary(int value)
    {
        int bitValue = 16;

        String intToBinary = Integer.toBinaryString(value);

        int zerosToAdd = bitValue - intToBinary.length();

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < zerosToAdd; i++)
        {
            sb.append("0");
        }

        sb.append(intToBinary);

        return sb.toString();
    }

    public static void ExecuteProgram()
    {
        System.out.print("Enter Assembly(.asm) File to compile: ");
        String message;
        String input = keyboard.nextLine();

        try
        {
            assembleFile(input);
            message = "Assemble another .asm file?(Y/N): ";
        }
        catch (IOException e)
        {
            message = "Unable to read from " + input + ", Try again?(Y/N): ";
            //System.out.println("Unable to read from " + temp + ", Try again?(Y/N): ");
        }

        System.out.print(message);
        input = keyboard.nextLine();
        System.out.println();

        if(input.equalsIgnoreCase("Y"))
        {
            main(null);
        }
        else
        {
            System.out.println("Goodbye!...");
        }

        //List<String> testList = readFile("TestFile.txt");
        // printList(testList);
    }

    /***
     * Method: Reads file
     * @param fileName
     * @return
     */
    public static void assembleFile(String fileName) throws IOException
    {
        ArrayList<String> fileContent = new ArrayList<String>();

        //
        Scanner inputStream;
        File file = new File(fileName);
        inputStream = new Scanner(file);

        System.out.println("Reading from " + file.getName());

        // First Pass
        while (inputStream.hasNextLine())
        {
            String line = inputStream.nextLine().trim();

            line = removeComments(line);

//            if(lineIsComment(line))
//            {
//                // Ignore
//            }
//            else if(lineIs_Ainstruction(line))
//            {
//                // Convert to A-Instruction
//            }
//            else if(lineIsC_Instruction(line))
//            {
//                // Convert to C-Instruction
//            }

            //String processedLine = process(inputStream.nextLine());

            //fileContent.add(processedLine);

            if(line != null)
            {
                fileContent.add(line);
            }
        }

        inputStream.close();

        // DEBUG:
       // printList(fileContent);

        writeFileAssembly(fileContent, fileName);
    }


    /***
     * Method: Removes // comments from a given line.
     * @param line
     * @return
     */
    private static String removeComments(String line)
    {
        char forwardSlash = '/';

        if(line.length() > 1)
        {
            // case: first 2 characters are forward slash.
            if(line.charAt(0) == forwardSlash && line.charAt(1) == forwardSlash)
            {
                return null;
            }

            // case: 2 forward slashes appears some time after the first character.
            for (int i = 1; i < line.length(); i++)
            {
                if(line.charAt(i) == forwardSlash && line.charAt(i-1) == forwardSlash)
                {
                    return line.substring(0, i-1).trim();
                }
            }
        }

        return line.trim();
    }

    private boolean lineIsComment(String line)
    {
        return false;
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


    public static void writeFileAssembly(List<String> list, String fileName)
    {

        String newFileName = fileName.substring(0, fileName.length() - 4) + ".hack";

        //Create object(stream) for output using the FileOutputStream class
        PrintWriter outputStream = null;

        try
        {
            outputStream = new PrintWriter(new FileOutputStream(newFileName));
        }

        //Check to see if file can be created or exists. If not, end program
        catch (FileNotFoundException e)
        {
            System.out.println("Error opening the file dataFile.");
            System.exit(0);
        }


        //Tell user you are writing out to file
        System.out.println("Writing to file.......");

        //Write out to file
        for(String string : list)
        {
            outputStream.println(string);
        }

        //Close output file
        outputStream.close();

        System.out.println("Process Complete, File saved as " + newFileName);
    }

}
