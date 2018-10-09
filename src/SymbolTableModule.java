import javafx.beans.binding.StringBinding;

import java.util.HashMap;

/**
 *  + SymbolTableModule()
 *  + addEntry(symbol:String, address:int) : void
 *  + contains(symbold:String) : boolean
 *  + getAddress(symbol: String) : int
 */
public class SymbolTableModule
{
    private HashMap<String, String> symbolHashMap;

    public SymbolTableModule()
    {
        symbolHashMap = new HashMap<String, String>();
    }

    public String label(String mnemonic)
    {
        return symbolHashMap.get(mnemonic);
    }

    public void put(String label, int address)
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
