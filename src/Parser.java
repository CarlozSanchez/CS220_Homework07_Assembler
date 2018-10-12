// File: Parser.java
// Programmer: Carlos Sanchez
// CS220 MW 3:30pm - 5:20pm
// Last Modified: 10/11/2018
// Version 2.00

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/***
 * Parser.java - Used for reading a given file name and parsing the contents of
 * each line into corresponding class values.
 */
public class Parser
{
    /********* VARIABLES ********/
    private File file;
    private Scanner inputStream;
    private int lineNumber;
    private String rawLine;

    private String cleanLine;
    private CommandType commandType;
    private String symbol;
    private String destMnemonic;
    private String compMnemonic;
    private String jumpMnemonic;


    /***
     * FULL CONSTRUCTOR: opens input file/stream and prepares to parse.
     * PRECONDITION: provided file is ASM file.
     * POSTCONDITION: if file can't be opened, ends program w/ error message.
     * @param fileName The file to open.
     */
    public Parser(String fileName) throws IOException
    {
        file = new File(fileName);
        inputStream = new Scanner(file);
        lineNumber = 0;

        resetFields();
    }

    /***
     * HELPER: resets data fields to initial values, used by contructor and advance().
     * POSTCONDITION: data fields are reset to initial values.
     */
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
     * PRECONDITION: file stream is open.
     * @return returns true if more commands, else closes stream.
     */
    public boolean hasMoreCommmands()
    {
        if (inputStream.hasNextLine())
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
     * METHOD: reads next line rom file and parses it into instance variables.
     * PRECONDITION: file stream is open, called only if hasMoreCommands().
     * POSTCONDITION: current instruction parts put into instance vars.
     */
    public void advance()
    {
        resetFields();
        rawLine = inputStream.nextLine();
        cleanLine();
        parseCommandType();
        parse();
        lineNumber++;
    }


    /***
     * METHOD: cleans rawLine by removing whitespace and comments.
     * PRECONDITION: advance() is called so rawLine has string to clean.
     * POSTCONDITION: cleanLine is updated with contents of rawLine without comments or whitespace.
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

    /***
     * DESCRIPTION: helper method parses line depending on instruction type.
     * PRECONDITION: advance() called so cleanLine has value.
     * POSTCONDITION: appropriate parts (instance vars) of instruction filled.
     */
    private void parse()
    {
        switch (this.commandType)
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
        if (commandType == CommandType.A_COMMAND)
        {
            symbol = cleanLine.substring(1, cleanLine.length());
        }
        else if (commandType == CommandType.L_COMMAND)
        {
            symbol = cleanLine.substring(1, cleanLine.length() - 1);
        }
    }

    /***
     * DESCRIPTION: helper method parses line to get comp part.
     * PRECONDITION: advance() called so cleanLine hash value, call for C-instructions only.
     * POSTCONDITION: compMnemonic set to appropriate value form instruction.
     */
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

    /***
     * METHOD: helper method parses line to get dest part.
     * PRECONDITION: advance() called so cleanLine has values, call for C-instruction only.
     * POSTCONDITION: destMnemonic set to appropriate value from instruction.
     */
    private void parseDest()
    {
        String[] lineSplit = cleanLine.split("=");

        if (lineSplit.length == 2)
        {
            destMnemonic = lineSplit[0];
        }
        else
        {
            destMnemonic = "null";
        }
    }

    /***
     * DESCRIPTION: helper method parses lnie to get jump part.
     * PRECONDITION: advance() called so cleanLine has value, call for C-instructions only.
     * POSTCONDITION: jumpMnemonic set to appropriate value from instruction.
     */
    private void parseJump()
    {
        String[] lineSplit = cleanLine.split(";");

        if (lineSplit.length == 2)
        {
            jumpMnemonic = lineSplit[1];
        }
        else
        {
            jumpMnemonic = "null";
        }
    }


    /////////////////////////////////  Getters //////////////////////////////////

    /***
     * METHOD: getter for command type.
     * PRECONDITION: cleanLine has been parsed (advance was called)
     * @return returns char for command type (N/A/C/L)
     */
    public CommandType getCommandType()
    {
        return this.commandType;
    }

    /***
     * METHOD: getter for symbol type.
     * PRECONDITION: cleanLine has been parsed (advance was called), call for labels only ( use getCommandType())
     * @return returns string for symbol name.
     */
    public String getSymbol()
    {
        return this.symbol;
    }

    /***
     * METHOD: getter for dest part of C-instruction
     * PRECONDITION: cleanLine has been parsed (advance was called), call for C-instructions only (use getCommandType())
     * @return returns mnemonic (ASM symbol) for dest part.
     */
    public String getDest()
    {
        return this.destMnemonic;
    }

    /***
     * DESCRIPTION: getter for comp part of C-instruction.
     * PRECONDITION: cleanLine has been parsed (advance was called), call for C-instructions only (use getCommandType())
     * @return mnemonic (ASM symbol) for comp part.
     */
    public String getComp()
    {
        return this.compMnemonic;
    }

    /***
     * DESCRIPTION: getter for jump part of C-instruction.
     * PRECONDITION: cleanLine hash been parsed ( advance was called), call for C-instruction only (use getCommandType())
     * @return mnemonic (ASM symbol) for jump part.
     */
    public String getJump()
    {
        return this.jumpMnemonic;
    }

    /////////////////////////////////////////// FOR DEBUGGIN ////////////////////////////////////////

    /***
     * DESCRIPTION: getter for string version of command type ( debuggin)
     * PRECONDITION: advance(0 and parse() have been called.
     * @return string version of command type.
     */
    public String getCommandTypeString()
    {
        return this.commandType.toString();
    }

    /***
     * DESCRIPTION: getter for rawLine for file (debugging)
     * PRECONDITION: advance() was called to put value from file in here.
     * @return string of current original line from file.
     */
    public String getRawLine()
    {
        return this.rawLine;
    }

    /***
     * DESCRIPTION: getter for cleanLine from file (debuggin)
     * PRECONDITION: advance() and cleanLine() were called
     * @return string of current clean instruction from file.
     */
    public String getCleanLine()
    {
        return this.cleanLine;
    }

    /***
     * DESCRITION: getter for lineNumber (debugging)
     * PRECONDITION: n/a
     * @return line number currently being processed from file.
     */
    public int getLineNumber()
    {
        return this.lineNumber;
    }
}

enum CommandType
{
    A_COMMAND, C_COMMAND, L_COMMAND, N_COMMAND
}