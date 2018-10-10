import java.util.HashMap;

/**
 *  + SymbolTableModule()
 *  + addEntry(symbol:String, address:int) : void
 *  + contains(symbold:String) : boolean
 *  + getAddress(symbol: String) : int
 */
public class SymbolTableModule
{
    private static final int INITIAL_ADDRESS = 64;

    private HashMap<String, String> symbolHashMap;
    private int memoryAddress;

    public SymbolTableModule()
    {
        symbolHashMap = new HashMap<String, String>();
        memoryAddress = INITIAL_ADDRESS;
    }

    public boolean contains(String symbol)
    {
        return symbolHashMap.containsKey(symbol);
    }

    /***
     * !!!!! fist this to find a memeory address that is not holding a value
     * @param variable
     * @return
     */
    public String addVariable(String variable)
    {
        String result = symbolHashMap.get(variable);

        if(result == null)
        {
            String memAddressInBinary = CodeModule.intTo16bitBinary(memoryAddress);
            symbolHashMap.put(variable, memAddressInBinary);
            memoryAddress++;

            // Debug
            //System.out.println("adding to symbol mem " + memoryAddress + ": " + variable + " | "
            //+ memAddressInBinary + "\n");
            return memAddressInBinary;
        }
        else
        {
            return result;
        }
    }

    public String getAddress(String symbol)
    {
        return symbolHashMap.get(symbol);
    }

    public void addEntry(String label, int address)
    {
        this.symbolHashMap.put(label, CodeModule.intTo16bitBinary(address));
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
