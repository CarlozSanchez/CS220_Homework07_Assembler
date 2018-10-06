import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AssemblerDriver
{
    public static void main(String[] args)
    {
        System.out.println("hello");
    }

    public ArrayList<String> readFile(String fileName)
    {
        ArrayList<String> fileContent = new ArrayList<String>();

        File file = null;
        FileInputStream inStream = null;

        try{
            file = new File(fileName);
            System.out.println("Reading " + file.getName());

            inStream = new FileInputStream(file);

           // while(inStream.has)
        }
        catch(IOException e)
        {
            System.out.println("Could not read from " + file.getName());
        }

        return fileContent;

    }

}
