// File: SymbolTable.java
// Programmer: Carlos Sanchez
// CS220 MW 3:30pm - 5:20pm
// Last Modified: 10/11/2018
// Version 2.00

import java.util.HashMap;

/**
 * + SymbolTableModule()
 * + addEntry(symbol:String, address:int) : void
 * + contains(symbold:String) : boolean
 * + getAddress(symbol: String) : int
 */
public class SymbolTable
{

    /*** Field ************/
    private HashMap<String, Integer> symbolHashMap;

    /***
     * METHOD: default constructor.
     */
    public SymbolTable()
    {
        symbolHashMap = new HashMap<String, Integer>();
        addPredefineSymbols();
    }

    public boolean contains(String symbol)
    {
        return symbolHashMap.containsKey(symbol);
    }

    public int getAddress(String symbol)
    {
        return symbolHashMap.get(symbol);
    }

    public boolean addEntry(String symbol, int address)
    {
        return this.symbolHashMap.put(symbol, address) != null;
    }

    private void addPredefineSymbols()
    {
        symbolHashMap.put("SP", 0);
        symbolHashMap.put("LCL", 1);
        symbolHashMap.put("ARG", 2);
        symbolHashMap.put("THIS", 3);
        symbolHashMap.put("THAT", 4);
        symbolHashMap.put("SCREEN", 16384);
        symbolHashMap.put("KBD", 24576);

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
