// Programmer: Carlos Sanchez
// CS220 MW 3:30pm - 5:20pm
// Last Modified: 10/7/2018
// Version 1.00



import java.io.*;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/***
 *  + ParserModule() :
 *  + ParserModule(fileName : String)
 *  + hasMoreCommands() : boolean
 *  + advance() : void
 *  + commandType : enum:command
 *  + symbol() : String
 *  + dest() : String
 *  + comp() : String
 *  + jump() : String
 */

enum CommandType
{
    A_COMMAND, C_COMMAND, L_COMMAND, N_COMMAND
}

public class ParserModule
{
    private static final String ROOT = "file/";
    private static final char FORWARD_SLASH = '/';


    File file;
    ArrayList<String> fileContent;
    int counter;

    /***
     * DEFAULT CONSTRUCTOR:
     */
    public ParserModule()
    {
        counter = 0;
        file = null;
        fileContent = new ArrayList<String>();
    }

    /***
     * fULL CONSTRUCTOR:
     * @param fileName
     */
    public ParserModule(String fileName)
    {
        counter = 0;
        fileContent = new ArrayList<String>();
        file = new File(ROOT + fileName);
    }


    /***
     *
     * @throws IOException
     */
    public void assembleFile() throws IOException, InvalidSyntaxException, NumberFormatException
    {

        counter = 0;
        Scanner inputStream = new Scanner(file);

        System.out.println("Reading from " + file.getName());

        // First Pass
        while (inputStream.hasNextLine())
        {
            counter++;
            CommandType command = null;

            String line = removeComments(inputStream.nextLine().trim());
           // boolean validLine = line != null && line.length() != 0;

            if (!line.isEmpty())
            {
                command = commandType(line);

//                if (command == null)
//                {
//                    throw new InvalidSyntaxException(counter);
//                }

                switch (command)
                {
                    case N_COMMAND:
                        // Do nothing
                        break;

                    case A_COMMAND:
                       // line += "  <- This is an A Command";
                        line = processA(line);
                        break;

                    case C_COMMAND:
                        //line += "  <- This is a C Command";
                        line = processC(line);
                        break;

                    case L_COMMAND:
                        line += "  <- This is an L Command";
                        //line = processL(line);
                        break;

                    default:
                        //line += "  <- Unkown Command";
                        System.out.println("Unkown Command ");
                        break;
                }

                if(line == null)
                {
                    throw new InvalidSyntaxException(counter);
                }

                fileContent.add(line);

            }
        }

        inputStream.close();

        // DEBUG:
        // printList(fileContent);

        writeFileAssembly(fileContent, file.getName());
    }

    private String processA(String line) //throws NumberFormatException
    {
        int address;

        try{
            address = Integer.parseInt(line.substring(1, line.length()));
            return CodeModule.intTo16bitBinary(address);
        }
        catch(NumberFormatException e)
        {
            // !!!! implements look up variables in table
            return line;
        }

           // return null;
    }

    private String processC(String line)
    {
        String[] assignment = line.split("=");

        if(assignment.length == 2)
        {
           // return "111" +
        }

        assignment = line.split(";");
        if(assignment.length == 2)
        {

        }

        return line;

    }

    private String processL(String line)
    {
        String temp = "";
        return temp;
    }

    private CommandType commandType(String line)
    {
        if(line == null || line.isEmpty())
        {
            return CommandType.N_COMMAND;
        }
        if (line.charAt(0) == '@')
        {
            return CommandType.A_COMMAND;
        }

        for (int i = 0; i < line.length(); i++)
        {
            if (line.charAt(i) == '=' || line.charAt(i) == ';')
            {
                return CommandType.C_COMMAND;
            }
        }

        if (line.charAt(0) == '(' && line.charAt(line.length() - 1) == ')')
        {
            return CommandType.L_COMMAND;
        }

        // return CommandType.L_COMMAND;
        return null;
    }

    /***
     * Method: Removes // comments from a given line.
     * @param line
     * @return
     */
    private static String removeComments(String line)
    {
        String str;

//        if (line.length() > 1)
//        {
//            // case1: first 2 characters are forward slash.
//            if (line.charAt(0) == FORWARD_SLASH && line.charAt(1) == FORWARD_SLASH)
//            {
//                return "";
//            }
//
//            // case2: forward slashes appears some time after the first character.
//            for (int i = 1; i < line.length(); i++)
//            {
//                if (line.charAt(i) == FORWARD_SLASH && line.charAt(i - 1) == FORWARD_SLASH)
//                {
//                    return line.substring(0, i - 1).trim();
//                }
//            }
//        }
        int commentLocation = line.indexOf("//");
        if(commentLocation != -1)
        {
            line = line.substring(0, commentLocation);
        }



        line = line.replaceAll(" ", "");
        line = line.replaceAll("\t", "");

        return line;
    }


    /***
     * METHOD: Writes machine code to .hack file.
     * @param list
     * @param fileName
     */
    public void writeFileAssembly(List<String> list, String fileName)
    {

        String newFileName = ROOT + fileName.substring(0, fileName.length() - 4) + ".hack";

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
        for (String string : list)
        {
            outputStream.println(string);
        }

        //Close output file
        outputStream.close();

        System.out.println("Process Complete, File saved at \"" + newFileName + "\"");
    }
}