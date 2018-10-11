// File: Code.java
// Programmer: Carlos Sanchez
// CS220 MW 3:30pm - 5:20pm
// Last Modified: 10/11/2018
// Version 2.00


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;


/**
 * + dest(input : String) : String
 * + comp(input : String) : String
 * + jump(input : String) : String
 */


enum WriteState
{
    DEST, JUMP, COMP
}


/***
 * Code.java - This class is used as a look up table to store and convert comp,
 * dest and jump mnemonics.  The key's are stored as a string and the values
 * are stored as a 7 or 3 bit value 1's and 0's in string form.
 */
public class Code
{
    private final String CODE_TABLE_FILE_NAME = "CodeTable.txt";


    private HashMap<String, String> destHashMap;
    private HashMap<String, String> compHashMap;
    private HashMap<String, String> jumpHashMap;


    /***
     * CONSTRUCTOR: initializes hashmaps with binary codes for easy lookup.
     * PRECONDIGION: comp codes = 7 bits (including a), dest/jump code = 3 bit
     * POSTCONDITION: all hashmaps have lookups for valid codes.
     */
    public Code()
    {
        destHashMap = new HashMap<String, String>();
        compHashMap = new HashMap<String, String>();
        jumpHashMap = new HashMap<String, String>();
        this.fillHashTable();
    }


    /***
     * ACCESSOR: converts to string of bits(3) for given mnemonic.
     * PRECONDITION: hashmaps are build with valid values.
     * @param mnemonic the mnemonic to convert.
     * @return returns string of bits if valid, else return null.
     */
    public String gestDest(String mnemonic)
    {
        return destHashMap.get(mnemonic);
    }

    /****
     * ACCESSOR: converts to string of bits(7) for given mnemonic.
     * PRECONDITION: hashmaps are build with valid values.
     * @param mnemonic the mnemonic to convert.
     * @return returns string of bits if valid, else return null.
     */
    public String getComp(String mnemonic)
    {
        return compHashMap.get(mnemonic);
    }

    /***
     * ACCESSOR: converts to string of bits(3) for given mnemonic.
     * PRECONDITION: hashmaps are build with valid values.
     * @param mnemonic the mnemonic to convert
     * @return a string of bits if valid, else return null.
     */
    public String getJump(String mnemonic)
    {
        return jumpHashMap.get(mnemonic);
    }

    /***
     * METHOD: Reads in CodeTable text file and puts KV pair of Symbol and Binaray equivalent.
     * PRECONDITION: The file to read is located in same directory as this file.
     * POSTCONDITION: dest, comp and jump hashmap are updated with KV pair mnemonic and binary code.
     */
    private void fillHashTable()
    {
        File file = new File(CODE_TABLE_FILE_NAME);
        Scanner inputStream = null;
        String line;

        try
        {
            inputStream = new Scanner(file);
        }
        catch (IOException e)
        {
            System.out.println("Unable to build hash table from " + file.getName());
        }

        WriteState writeState = WriteState.COMP;

        while (inputStream.hasNextLine())
        {
            line = inputStream.nextLine().trim();


            if (!line.isEmpty() && line.contains("//"))
            {
                if (line.contains("Comp"))
                {
                    //System.out.println("debug: " + line.substring(begin, end));
                    writeState = WriteState.COMP;
                }
                else if (line.contains("Dest"))
                {
                    writeState = WriteState.DEST;
                }

                else if (line.contains("Jump"))
                {
                    writeState = WriteState.JUMP;
                }
            }

            if (!line.isEmpty() && !line.contains("//"))
            {
                String[] lineArray = line.split("_");

                switch (writeState)
                {
                    case COMP:
                        compHashMap.put(lineArray[0], lineArray[1]);
                        break;

                    case DEST:
                        destHashMap.put(lineArray[0], lineArray[1]);
                        break;

                    case JUMP:
                        jumpHashMap.put(lineArray[0], lineArray[1]);
                        break;

                    default:
                        //System.out.println("Debug: " + line + "WriteState: " + writeState);
                        System.out.println("Unkown Write State");
                }
            }
        }
    }


    /**
     * METHOD: Converts an Integer value into it's equivalent 15-bit binary value.
     * PRECONDITION: deciman value given is a non negative number.
     *
     * @param decimal the value to conver to binary(must be non negative).
     * @return a String of 1's & 0's representing the decimal value.
     */
    public static String decimalTo15BitBinary(int decimal)
    {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < 15; i++)
        {
            if (decimal % 2 == 0)
            {
                str.append(0);
            }
            else
            {
                str.append(1);
            }

            decimal /= 2;
        }

        return str.reverse().toString();
    }


    /***
     * METHOD: override toString() method that returns the contents of hashmaps.
     * @return a string with all the KV pairs in comp, dest, jump hashmaps.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("--- Comp HashMap ---\n");
        for (String key : compHashMap.keySet())
        {
            String str = String.format("%-5s | %s\n", key, compHashMap.get(key));
            // sb.append(key + " | " + hashMap.get(key) + "\n");
            sb.append(str);

        }
        sb.append("\n");

        sb.append("--- Dest HashMap ---\n");
        for (String key : destHashMap.keySet())
        {
            String str = String.format("%-5s | %s\n", key, destHashMap.get(key));
            // sb.append(key + " | " + hashMap.get(key) + "\n");
            sb.append(str);

        }
        sb.append("\n");

        sb.append("--- Jump HashMap ---\n");
        for (String key : jumpHashMap.keySet())
        {
            String str = String.format("%-5s | %s\n", key, jumpHashMap.get(key));
            // sb.append(key + " | " + hashMap.get(key) + "\n");
            sb.append(str);

        }
        sb.append("\n");

        return sb.toString();
    }
}
