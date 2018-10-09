import javafx.scene.input.Mnemonic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * + dest(input : String) : String
 * + comp(input : String) : String
 * + jump(input : String) : String
 *
 */
enum WriteState{DEST, JUMP, COMP, LABEL}

public class CodeModule
{
    private HashMap<String, String> destHashMap;
    private HashMap<String, String> compHashMap;
    private HashMap<String, String> jumpHashMap;
    private HashMap<String, String> labelHashMap;

    WriteState writeState;


    public CodeModule()
    {
        destHashMap = new HashMap<String, String>();
        compHashMap = new HashMap<String, String>();
        jumpHashMap = new HashMap<String, String>();
        labelHashMap = new HashMap<String, String>();

        writeState = WriteState.COMP;

        this.fillHashTable();
    }

    public String dest(String mnemonic)
    {
        return destHashMap.get(mnemonic);
    }

    public String comp(String mnemonic)
    {
        return compHashMap.get(mnemonic);
    }

    public String jump(String mnemonic)
    {
        return jumpHashMap.get(mnemonic);
    }

    public String label(String mnemonic)
    {
        return labelHashMap.get(mnemonic);
    }


    public void fillHashTable()
    {
        String fileLocation = "file/AssemblyCodeModule.txt";
        File file = new File(fileLocation);
        Scanner inputStream = null;
        String line;

        try
        {
             inputStream = new Scanner(file);
        }
        catch(IOException e)
        {
            System.out.println("Unable to build hash table from " + file.getName());
        }

        while(inputStream.hasNextLine())
        {
            int begin = 2;
            int end = 6;

            line = inputStream.nextLine().trim();


            if(!line.isEmpty() && line.charAt(0) == '/' && line.charAt(1) == '/')
            {
                if(line.substring(begin, end).equalsIgnoreCase("comp"))
                {
                    //System.out.println("debug: " + line.substring(begin, end));
                    writeState = WriteState.COMP;
                }
                else if(line.substring(begin,end).equalsIgnoreCase("dest"))
                {
                    writeState = WriteState.DEST;
                }

                else if(line.substring(begin, end).equalsIgnoreCase("jump"))
                {
                    writeState = WriteState.JUMP;
                }
                else if(line.substring(begin, end).equalsIgnoreCase("labe"))
                {
                    writeState = WriteState.LABEL;
                }
            }

            if( !line.isEmpty() && line.charAt(0) != '/' && line.charAt(1) != '/')
            {
                String[] lineArray = line.split("_");

                switch(writeState)
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

                    case LABEL:
                        labelHashMap.put(lineArray[0], lineArray[1]);

                        default:
                            System.out.println("Unkown Write State");
                }
            }
        }

        addRegistersToTable();
    }


    private void addRegistersToTable()
    {
        for(int i = 0; i < 16; i++)
        {
            String key = "R" + i;
            String value = intTo16bitBinary(i);
            labelHashMap.put(key, value);
        }
    }

    /**
     * Method: Converts an Integer value into it's equivalent 15-bit binary value.
     * Range of 0 - 65,535
     *
     * @param
     * @return
     */
    public static String intTo16bitBinary(int toConvert)
    {

        int bitValue = 16;

        StringBuilder str = new StringBuilder();

        for(int i = 0; i < 15; i++)
        {
            if( toConvert % 2 == 0)
            {
                str.append(0);
            }
            else
            {
                str.append(1);
            }

            toConvert /= 2;
        }

        return str.reverse().toString();



//        str.reverse();
//
//        String intToBinary = Integer.toBinaryString(value);
//
//        int zerosToAdd = bitValue - intToBinary.length();
//
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < zerosToAdd; i++)
//        {
//            sb.append("0");
//        }
//
//        sb.append(intToBinary);
//
//        return sb.toString();
    }


    public String toString()
    {
        StringBuilder sb  = new StringBuilder();

        sb.append("--- Comp HashMap ---\n");
        for(String key : compHashMap.keySet())
        {
            String str = String.format("%-5s | %s\n", key, compHashMap.get(key));
           // sb.append(key + " | " + hashMap.get(key) + "\n");
            sb.append(str);

        }
        sb.append("\n");

        sb.append("--- Dest HashMap ---\n");
        for(String key : destHashMap.keySet())
        {
            String str = String.format("%-5s | %s\n", key, destHashMap.get(key));
            // sb.append(key + " | " + hashMap.get(key) + "\n");
            sb.append(str);

        }
        sb.append("\n");

        sb.append("--- Jump HashMap ---\n");
        for(String key : jumpHashMap.keySet())
        {
            String str = String.format("%-5s | %s\n", key, jumpHashMap.get(key));
            // sb.append(key + " | " + hashMap.get(key) + "\n");
            sb.append(str);

        }
        sb.append("\n");

        sb.append("--- Label hashMap --\n");
        for(String key : labelHashMap.keySet())
        {
            String str = String.format("%-5s | %s\n", key, labelHashMap.get(key));
            // sb.append(key + " | " + hashMap.get(key) + "\n");
            sb.append(str);

        }
        sb.append("\n");


        return sb.toString();
    }
}
