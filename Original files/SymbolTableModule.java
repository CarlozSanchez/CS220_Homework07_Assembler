import java.util.HashMap;

/**
 *  + SymbolTableModule()
 *  + addEntry(symbol:String, address:int) : void
 *  + contains(symbold:String) : boolean
 *  + getAddress(symbol: String) : int
 */
public class SymbolTableModule
{
    //private static final int INITIAL_ADDRESS = 64;

    private HashMap<String, Integer> symbolHashMap;
   // private int memoryAddress;

    public SymbolTableModule()
    {
        symbolHashMap = new HashMap<String, Integer>();
        //memoryAddress = INITIAL_ADDRESS;
        addPredefineSymbols();
    }

    public boolean contains(String symbol)
    {
        return symbolHashMap.containsKey(symbol);
    }

//    /***
//     * !!!!! fist this to find a memeory address that is not holding a value
//     * @param variable
//     * @return
//     */
//    public String addVariable(String variable)
//    {
//        String result = symbolHashMap.get(variable);
//
//        if(result == null)
//        {
//            String memAddressInBinary = CodeModule.intTo16bitBinary(memoryAddress);
//            symbolHashMap.put(variable, memAddressInBinary);
//            memoryAddress++;
//
//            // Debug
//            //System.out.println("adding to symbol mem " + memoryAddress + ": " + variable + " | "
//            //+ memAddressInBinary + "\n");
//            return memAddressInBinary;
//        }
//        else
//        {
//            return result;
//        }
//    }

    public int getAddress(String symbol)
    {
        return symbolHashMap.get(symbol);
    }

    public boolean addEntry(String symbol, int address)
    {
       return  this.symbolHashMap.put(symbol, address) != null;
    }

    private void addPredefineSymbols()
    {
        symbolHashMap.put("SP",0);
        symbolHashMap.put("LCL",1 );
        symbolHashMap.put("ARG",2 );
        symbolHashMap.put("THIS",3 );
        symbolHashMap.put("THAT",4 );
        symbolHashMap.put("SCREEN",16384);
        symbolHashMap.put("KBD",24576);

        addRegistersToTable();
    }

    private void addRegistersToTable()
    {
        for (int i = 0; i < 16; i++)
        {
            String key = "R" + i;
            symbolHashMap.put(key, i);
        }
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("--- Symbol Table hashMap --\n");
        for (String key : symbolHashMap.keySet())
        {
            String str = String.format("%-5s | %s\n", key, symbolHashMap.get(key));
            // sb.append(key + " | " + hashMap.get(key) + "\n");
            sb.append(str);

        }
        sb.append("\n");

        return sb.toString();
    }
}
