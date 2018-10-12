// File: Parser.java
// Programmer: Carlos Sanchez
// CS220 MW 3:30pm - 5:20pm
// Last Modified: 10/11/2018
// Version 2.00


//TODO: don't forget to document each method in all classes!

import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;

public class Assembler
{

    // ALGORITHM:
    // get input file name
    // create output file name and stream

    // create symbol table
    // do first pass to build symbol table (no output yet!)
    // do second pass to output translated ASM to HACK code

    // print out "done" message to user
    // close output file stream
    public static void main(String[] args)
    {
        String inputFileName, outputFileName;
        PrintWriter outputFile = null; //keep compiler happy
        SymbolTable symbolTable;
        int romAddress, ramAddress;

        //get input file name from command line or console input
        if (args.length == 1)
        {
            System.out.println("command line arg = " + args[0]);
            inputFileName = args[0];
        }
        else
        {
            Scanner keyboard = new Scanner(System.in);

            System.out.println("Please enter assembly file name you would like to assemble.");
            System.out.println("Don't forget the .asm extension: ");
            inputFileName = keyboard.nextLine();

            keyboard.close();
        }

        outputFileName = inputFileName.substring(0, inputFileName.lastIndexOf('.')) + ".hack";

        try
        {
            outputFile = new PrintWriter(new FileOutputStream(outputFileName));
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("Could not open output file " + outputFileName);
            System.err.println("Run program again, make sure you have write permissions, etc.");
            System.exit(0);
        }

        // TODO: finish driver as algorithm describes

        symbolTable = new SymbolTable();

        firstPass(inputFileName, symbolTable);
        secondPass(inputFileName, symbolTable, outputFile);

        System.out.println("Process complete, output saved as " + outputFileName);
        outputFile.close();
    }

    // TODO: march through the source code without generating any code
    //for each label declaration (LABEL) that appears in the source code,
    // add the pair <LABEL, n> to the symbol table
    // n = romAddress which you should keep track of as you go through each line
    //HINT: when should rom address increase? What kind of commands?
    private static void firstPass(String inputFileName, SymbolTable symbolTable)
    {
        Parser parser = null;
        try
        {
            parser = new Parser(inputFileName);
        }
        catch (IOException e)
        {
            System.out.println("Unable to open file " + inputFileName + " Goodbye!!!");
        }

        System.out.println("Executing first pass...");

        int romAddress = 0;
        boolean entryAdded;

        while (parser.hasMoreCommmands())
        {
            parser.advance();

            if (parser.getCommandType() == CommandType.L_COMMAND)
            {
                entryAdded = symbolTable.addEntry(parser.getSymbol(), romAddress);

                if (entryAdded == false)
                {
                    System.out.println("Invalid Syntax detected: \"" + parser.getSymbol() + "\" at Line " + parser.getLineNumber() + ": " + parser.getRawLine());
                    System.out.println("Exiting program");
                    System.exit(0);
                }
            }
            else if (parser.getCommandType() == CommandType.A_COMMAND || parser.getCommandType() == CommandType.C_COMMAND)
            {
                romAddress++;
            }
        }
    }

    // TODO: march again through the source code and process each line:
    // if the line is a c-instruction, simple (translate)
    // if the line is @xxx where xxx is a number, simple (translate)
    // if the line is @xxx and xxx is a symbol, look it up in the symbol
    //	table and proceed as follows:
    // If the symbol is found, replace it with its numeric value and
    //	and complete the commands translation
    // If the symbol is not found, then it must represent a new variable:
    // add the pair <xxx, n> to the symbol table, where n is the next
    //	available RAM address, and complete the commands translation
    // HINT: when should rom address increase?  What should ram address start
    // at? When should it increase?  What do you do with L commands and No commands?
    private static void secondPass(String inputFileName, SymbolTable symbolTable, PrintWriter outputFile)
    {
        Parser parser = null;

        Code code = new Code();

        try
        {
            parser = new Parser(inputFileName);
        }
        catch (IOException e)
        {
            System.out.println("Unable to open file " + inputFileName + " Goodbye!!!");
        }

        System.out.println("Executing Second pass...");


        int ramAddress = 16;

        while (parser.hasMoreCommmands())
        {
            parser.advance();

            if (parser.getCommandType() == CommandType.A_COMMAND)
            {
                try
                {
                    int address = Integer.parseInt(parser.getSymbol());
                    outputFile.write("0" + Code.decimalTo15BitBinary(address) + "\n");
                }
                catch (NumberFormatException e)
                {
                    if (!symbolTable.contains(parser.getSymbol()))
                    {
                        boolean entryAdded = symbolTable.addEntry(parser.getSymbol(), ramAddress);

                        if (entryAdded == false)
                        {
                            System.out.println("Invalid Syntax detected: \"" + parser.getSymbol() + "\" at Line " + parser.getLineNumber() + ": " + parser.getRawLine());
                            System.out.println("Exiting program");
                            System.exit(0);
                        }

                        ramAddress++;
                    }

                    String addressStr = Code.decimalTo15BitBinary(symbolTable.getAddress(parser.getSymbol()));
                    outputFile.write("0" + addressStr + "\n");
                }
            }
            else if (parser.getCommandType() == CommandType.C_COMMAND)
            {
                String instruction = "111";

                String comp = code.getComp(parser.getComp());
                String dest = code.getDest(parser.getDest());
                String jump = code.getJump(parser.getJump());

                outputFile.write(instruction + comp + dest + jump + "\n");
            }
        }
    }
}