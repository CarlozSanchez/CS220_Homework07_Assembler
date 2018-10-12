import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ScratchPad
{

//    public void assembleFile() throws IOException, NumberFormatException, InvalidSyntaxException
//    {
//        ArrayList<String> list;
//
//        list = firstPass();
//
//        writeFileAssembly(list, "firstPass");
//        list = secondPass(list);
//
//        writeFileAssembly(list, file.getName());
//    }
//
//
//
//    /***
//     *
//     * @throws IOException
//     */
//    public ArrayList<String> firstPass() throws NumberFormatException, InvalidSyntaxException
//    {
//        System.out.println("Reading from " + file.getName());
//
//        // First Pass
//        while (inputStream.hasNextLine())
//        {
//            CommandType command = null;
//
//            String cleanLine = cleanLine(inputStream.nextLine());
//
//            command = getCommandType(cleanLine);
//
//
//
//
//            if (command != CommandType.N_COMMAND && command != CommandType.L_COMMAND)
//            {
//                fileContent.add(cleanLine);
//            }
//
//            asmCounter++;
//        }
//
//        inputStream.close();
//        return fileContent;
//    }
//
//
//    private ArrayList<String> secondPass(ArrayList<String> fileContent)
//    {
//        ArrayList<String> arrayList = new ArrayList<String>(fileContent.size());
//
//        for (String str : fileContent)
//        {
//            if (str.charAt(0) == '@')
//            {
//                String symbol = str.substring(1, str.length());
//
//                String address = CodeModule.intTo15bitBinary(symbolTable.getAddress(symbol));
//
//                if (address != null)
//                {
//                    arrayList.add("0" + address);
//                }
//            }
//            else
//            {
//                arrayList.add(str);
//            }
//        }
//        return arrayList;
//    }
//
//
//
//    private void processL(String line, int address)
//    {
//        line = line.substring(1, line.length() - 1);
//        //symbolTable.addEntry(line, address);
//    }
//
//    private String processA(String line)
//    {
//        String subString = line.substring(1, line.length());
//
//        try // to convert parameter to Integer
//        {
//            int address = Integer.parseInt(subString);
//            return "0" + CodeModule.intTo15bitBinary(address);
//        }
//        catch (NumberFormatException e)
//        {
//            String symbol = symbolTable.put(subString);
//            if (symbol != null)
//            {
//                return "0" + symbol;
//            }
//            else
//            {
//                symbolTable.addVariable(subString);
//                return line;
//            }
//        }
//    }

    private String processC(String line, Code code) //throws InvalidSyntaxException
    {
        String instruction = "111";
        String comp;
        String dest = "000";
        String jump = "000";
        String[] lineSplit = line.split("=");

        // Process a computation Command
        if (lineSplit.length == 2)
        {

            dest = code.getDest(lineSplit[0]);
            comp = code.getComp(lineSplit[1]);


            if (dest != null && comp != null)
            {
                return instruction + comp + dest + jump;
            }
        }

        // Process a Jump Command
        lineSplit = line.split(";");
        if (lineSplit.length == 2)
        {
            comp = code.getComp(lineSplit[0]);
            jump = code.getJump(lineSplit[1]);

            if (dest != null && jump != null)
            {
                return instruction + comp + dest + jump;
            }
        }

       // throw new InvalidSyntaxException();
        return line;
    }




    /***
     * METHOD: Writes machine code to .hack file.
     * @param list
     * @param fileName
     */
    public void writeFileAssembly(List<String> list, String fileName)
    {

        String newFileName = fileName.substring(0, fileName.length() - 4) + ".hack";

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
