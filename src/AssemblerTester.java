import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssemblerTester
{


    public static void compareFiles(String fileName)
    {
        System.out.print("Enter file name without extension that you want to compare: ");


        String result = fileName + ".hack equals " + fileName + ".comp ?: ";
        result += compareBinaryFilesEqual(fileName);

        System.out.println(result);
    }


    private static boolean compareBinaryFilesEqual(String fileName)
    {
        File fileA = new File(fileName + ".hack");
        File fileB = new File( fileName + ".cmp");

        ArrayList<String> listA = createArrayList(fileA);
        ArrayList<String> listB = createArrayList(fileB);

        ArrayList<String> result = new ArrayList<>();

        boolean listsAreEqual = true;

        for(int i = 0; i < listA.size() ; i++)
        {
            if(!listA.get(i).equals(listB.get(i)))
            {
                listsAreEqual = false;
                result.add(String.format("[%d] %s != %s\n", i+1, listA.get(i), listB.get(i)));
            }
        }

        for(String str: result)
        {
            System.out.println(result);
        }


        return listsAreEqual;

    }

    private static ArrayList<String> createArrayList(File file)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        Scanner inputStream = null;

        try
        {
            inputStream = new Scanner(file);
        }
        catch(IOException e)
        {
            System.out.println("Unable to opent" + file.getName());
        }

        while(inputStream.hasNextLine())
        {
            arrayList.add(inputStream.nextLine());
        }

        return arrayList;
    }

    /***
     * Method: Prints the contents of List for testing purposes.
     * @param list The list to print contents
     */
    public static void printList(List<String> list)
    {
        try
        {
            for (String string : list)
            {
                System.out.println(string);
            }
        }
        catch (Exception e)
        {
            System.out.println("Could not print List content");
        }
    }

    public static void testSplit()
    {

        String str = "D*A";
        String[] result = str.split("=");

        for (int i = 0; i < result.length; i++)
        {
            System.out.println(i + ": " + result[i]);
        }

    }

    public static void test()
    {
        //System.out.println(intTo16bitBinary(65535));
        CodeModule codeModule = new CodeModule();
        System.out.println(codeModule);

        String mnemonic = "R1";
       // System.out.println("Translating  " + mnemonic + ": " + codeModule.label(mnemonic));

        String test = "//Destination";
        System.out.println(test.substring(2, 6));
    }

}
