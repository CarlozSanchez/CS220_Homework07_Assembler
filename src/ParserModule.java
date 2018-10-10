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
    CodeModule codeModule = new CodeModule();
    SymbolTableModule symbolTable = new SymbolTableModule();

    /***
     * DEFAULT CONSTRUCTOR:
     */
    public ParserModule()
    {
        file = null;
        fileContent = new ArrayList<String>();
    }

    /***
     * fULL CONSTRUCTOR:
     * @param fileName
     */
    public ParserModule(String fileName)
    {
        fileContent = new ArrayList<String>();
        file = new File(ROOT + fileName);
    }

    public void assembleFile() throws IOException, NumberFormatException, InvalidSyntaxException
    {
        ArrayList<String> list;

        list = firstPass();

        writeFileAssembly(list, "firstPass");
        list = secondPass(list);

        writeFileAssembly(list, file.getName());
    }

    /***
     *
     * @throws IOException
     */
    public ArrayList<String> firstPass() throws IOException, NumberFormatException, InvalidSyntaxException
    {

        int asmCounter = 0;
        int hackCounter = 0;
        Scanner inputStream = new Scanner(file);

        System.out.println("Reading from " + file.getName());

        // First Pass
        while (inputStream.hasNextLine())
        {
            CommandType command = null;

            String cleanLine = cleanLine(inputStream.nextLine());

            command = commandType(cleanLine);

            switch (command)
            {
                case N_COMMAND:
                    // Do nothing
                    break;

                case A_COMMAND:
                    // line += "  <- This is an A Command";
                    cleanLine = processA(cleanLine);
                    hackCounter++;
                    break;

                case C_COMMAND:
                    //line += "  <- This is a C Command";
                    try
                    {
                        cleanLine = processC(cleanLine);
                    }
                    catch (InvalidSyntaxException e)
                    {
                        throw new InvalidSyntaxException(asmCounter);
                    }

                    hackCounter++;
                    break;

                case L_COMMAND:
                    //line += "  <- This is an L Command";
                    processL(cleanLine, hackCounter);
                    break;

                default:
                    //line += "  <- Unknown Command";
                    System.out.println("Unknown Command ");
                    break;
            }


            if (command != CommandType.N_COMMAND && command != CommandType.L_COMMAND)
            {
                fileContent.add(cleanLine);
            }

            asmCounter++;
        }

        inputStream.close();
        return fileContent;
    }

    private ArrayList<String> secondPass(ArrayList<String> fileContent)
    {
        ArrayList<String> arrayList = new ArrayList<String>(fileContent.size());

        for (String str : fileContent)
        {
            if (str.charAt(0) == '@')
            {
                String symbol = str.substring(1, str.length());

                String address = symbolTable.getAddress(symbol);

                if (address != null)
                {
                    arrayList.add("0" + address);
                }
            }
            else
            {
                arrayList.add(str);
            }
        }
        return arrayList;
    }


    private String processA(String line)
    {
        String subString = line.substring(1, line.length());

        try
        {
            int address = Integer.parseInt(subString);
            return "0" + CodeModule.intTo16bitBinary(address);
        }
        catch (NumberFormatException e)
        {
            String symbol = codeModule.label(subString);
            if (symbol != null)
            {
                return "0" + symbol;
            }
            else
            {
                symbolTable.addVariable(subString);
                return line;
            }
        }
    }

    private String processC(String line) throws InvalidSyntaxException
    {
        String instruction = "111";
        String comp;
        String dest = "000";
        String jump = "000";
        String[] lineSplit = line.split("=");

        // Process a computation Command
        if (lineSplit.length == 2)
        {
            dest = codeModule.dest(lineSplit[0]);
            comp = codeModule.comp(lineSplit[1]);

            if (dest != null && comp != null)
            {
                return instruction + comp + dest + jump;
            }
        }

        // Process a Jump Command
        lineSplit = line.split(";");
        if (lineSplit.length == 2)
        {
            comp = codeModule.comp(lineSplit[0]);
            jump = codeModule.jump(lineSplit[1]);

            if (dest != null && jump != null)
            {
                return instruction + comp + dest + jump;
            }
        }

        throw new InvalidSyntaxException();
    }

    private void processL(String line, int address)
    {
        line = line.substring(1, line.length() - 1);
        symbolTable.addEntry(line, address);
    }


    private CommandType commandType(String line)
    {
        if (line == null || line.isEmpty())
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
    private static String cleanLine(String line)
    {
        line = line.replaceAll(" ", "");
        line = line.replaceAll("\t", "");

        int commentLocation = line.indexOf("//");
        if (commentLocation != -1)
        {
            line = line.substring(0, commentLocation);
        }

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