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
 */
enum WriteState
{
    DEST, JUMP, COMP, LABEL
}

public class CodeModule
{
    private HashMap<String, String> destHashMap;
    private HashMap<String, String> compHashMap;
    private HashMap<String, String> jumpHashMap;
    private HashMap<String, String> labelHashMap;


    public CodeModule()
    {
        destHashMap = new HashMap<String, String>();
        compHashMap = new HashMap<String, String>();
        jumpHashMap = new HashMap<String, String>();
        labelHashMap = new HashMap<String, String>();
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


    private void fillHashTable()
    {
        String fileLocation = "file/AssemblyCodeModule.txt";
        File file = new File(fileLocation);
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
                else if (line.contains("Labels"))
                {
                    writeState = WriteState.LABEL;
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

                    case LABEL:
                        labelHashMap.put(lineArray[0], lineArray[1]);
                        break;

                    default:
                        //System.out.println("Debug: " + line + "WriteState: " + writeState);
                        System.out.println("Unkown Write State");
                }
            }
        }

        addRegistersToTable();
    }


    private void addRegistersToTable()
    {
        for (int i = 0; i < 16; i++)
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
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < 15; i++)
        {
            if (toConvert % 2 == 0)
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
    }


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

        sb.append("--- Label hashMap --\n");
        for (String key : labelHashMap.keySet())
        {
            String str = String.format("%-5s | %s\n", key, labelHashMap.get(key));
            // sb.append(key + " | " + hashMap.get(key) + "\n");
            sb.append(str);

        }
        sb.append("\n");

        return sb.toString();
    }
}
