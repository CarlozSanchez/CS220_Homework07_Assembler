// Programmer: Carlos Sanchez
// CS220 MW 3:30pm - 5:20pm
// Last Modified: 10/7/2018
// Version 2.00


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
    File file;

    // Added stuff
    Scanner inputStream;
    int lineNumber;
    String rawLine;

    String cleanLine;
    CommandType commandType;
    String symbol;
    String destMnemonic;
    String compMnemonic;
    String jumpMnemonic;


    /***
     * fULL CONSTRUCTOR:
     * @param fileName
     */
    public ParserModule(String fileName) throws IOException
    {
        file = new File(fileName);
        inputStream = new Scanner(file);
        lineNumber = 0;

        resetFields();
    }

    private void resetFields()
    {
        rawLine = "";
        cleanLine = "";
        commandType = null;
        symbol = "";
        destMnemonic = "";
        compMnemonic = "";
        jumpMnemonic = "";
    }

    /***
     * METHOD: checks if current input stream has next line to read.
     * @return returns false if we reach end of file, true if next line is available.
     */
    public boolean hasMoreCommmands()
    {
        if ( inputStream.hasNextLine())
        {
            return true;
        }

        else
        {
            inputStream.close();
            return false;
        }
    }

    /**
     * METHOD: advances to the next line in input stream and updates the class variables
     * based on Commandtype read.
     */
    public void advance()
    {
        rawLine = inputStream.nextLine();
        cleanLine();
        parseCommandType();
        parse();


        lineNumber++;
    }


    /***
     * Method: Removes // comments from a given line.
     * @param
     * @return
     */
    private void cleanLine()
    {
        String rawLine = this.rawLine;

        String line = rawLine.replaceAll(" ", "");
        line = line.replaceAll("\t", "");

        int commentLocation = line.indexOf("//");
        if (commentLocation != -1)
        {
            line = line.substring(0, commentLocation);
        }

        cleanLine = line;
    }

    private void parseCommandType()
    {
        if (cleanLine == null || cleanLine.isEmpty())
        {
            commandType = CommandType.N_COMMAND;
        }
        else if (cleanLine.charAt(0) == '@')
        {
            commandType = CommandType.A_COMMAND;
        }

        else if (cleanLine.charAt(0) == '(' && cleanLine.charAt(cleanLine.length() - 1) == ')')
        {
            commandType = CommandType.L_COMMAND;
        }
        else
        {
            for (int i = 0; i < cleanLine.length(); i++)
            {
                if (cleanLine.charAt(i) == '=' || cleanLine.charAt(i) == ';')
                {
                    commandType = CommandType.C_COMMAND;
                }
            }
        }

    }

    private void parse()
    {
        switch (commandType)
        {
            case N_COMMAND:
                // Do nothing
                break;

            case A_COMMAND:
                parseSymbol();
                break;

            case C_COMMAND:
                parseComp();
                parseDest();
                parseJump();
                break;

            case L_COMMAND:
                parseSymbol();
                break;

            default:
                System.out.println("Unknown Command ");
                break;
        }
    }

    private void parseSymbol()
    {
        if(commandType == CommandType.A_COMMAND)
        {
            symbol = cleanLine.substring(1, cleanLine.length());
        }
        else if(commandType == CommandType.L_COMMAND)
        {
            symbol = cleanLine.substring(1, cleanLine.length() - 1);
        }
    }

    private void parseComp()
    {
        String[] splitOnAssignment = cleanLine.split("=");
        String[] splitOnSemicolon = cleanLine.split(";");

        if (splitOnAssignment.length == 2)
        {
            compMnemonic = splitOnAssignment[1];
        }
        else if (splitOnSemicolon.length == 2)
        {
            compMnemonic = splitOnSemicolon[0];
        }
        else
        {
            compMnemonic = "null";
        }

    }

    private void parseDest()
    {
        String[] lineSplit = cleanLine.split("=");

        if (lineSplit.length == 2)
        {
            compMnemonic = lineSplit[0];
        }
        else
        {
            compMnemonic = "null";
        }
    }

    private void parseJump()
    {
        String[] lineSplit = cleanLine.split(";");

        if (lineSplit.length == 2)
        {
            jumpMnemonic = lineSplit[1];
        }
        else
        {
            compMnemonic = "null";
        }
    }


    /////////////////////////////////  Getters //////////////////////////////////

    public CommandType getCommandType()
    {
        return this.commandType;
    }

    public String getSymbol()
    {
        return this.symbol;
    }

    public String getDest()
    {
        return this.destMnemonic;
    }

    public String getComp()
    {
        return this.compMnemonic;
    }

    public String getJump()
    {
        return this.jumpMnemonic;
    }

    /////////////////////////////////////////// FOR DEBUGGIN ////////////////////////////////////////

    public String getCommandTypeString()
    {
        return this.commandType.toString();
    }

    public String getRawLine()
    {
        return this.rawLine;
    }

    public String getCleanLine()
    {
        return this.cleanLine;
    }

    public int getLineNumber()
    {
        return this.lineNumber;
    }


}