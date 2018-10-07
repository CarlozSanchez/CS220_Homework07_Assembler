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

public class CodeModule
{
    HashMap<String, String> hashMap;

    public CodeModule()
    {
        hashMap = new HashMap<String, String>();
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
            line = inputStream.nextLine().trim();
            if( !line.isEmpty() && line.charAt(0) != '/' && line.charAt(1) != '/')
            {
                String[] lineArray = line.split("_");
                hashMap.put(lineArray[0], lineArray[1]);
            }
        }
    }

    public String toString()
    {
        StringBuilder sb  = new StringBuilder();

        for(String key : hashMap.keySet())
        {
            String str = String.format("%-5s | %s\n", key, hashMap.get(key));
           // sb.append(key + " | " + hashMap.get(key) + "\n");
            sb.append(str);

        }

        return sb.toString();
    }
}
