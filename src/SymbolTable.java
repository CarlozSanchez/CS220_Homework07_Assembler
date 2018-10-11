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

/***
 * SymbolTable.java - Used to store and look up Key/Value pair of symbols/address.
 */
public class SymbolTable
{

    /************** Field ************/
    private HashMap<String, Integer> symbolHashMap;

    /***
     * DEFAULT CONSTRUCTOR: initializes hashmap with predefined symbols.
     * PRECONDITION: follows symbols/values from book/appendix.
     * POSTCONDITION: all hashmap values ahve valid address integer.
     */
    public SymbolTable()
    {
        symbolHashMap = new HashMap<String, Integer>();
        addPredefineSymbols();
    }


    /***
     * METHOD: adds new pair of symbol/address to hashmap.
     * PRECONDITION: symbol/address pair not in hashmap(check contains() 1st).
     * @param symbol the symbol to add as key.
     * @param address the address to add as value.
     * @return returns true if addes, false if illegal name;
     */
    public boolean addEntry(String symbol, int address)
    {
        return this.symbolHashMap.put(symbol, address) != null;
    }

    /***
     * METHOD: returns boolean of whether hashmap has symbol or not.
     * PRECONDITION: symbol is in hashmap (check w/ contains() first).
     * @param symbol The symbol to check for in hashmap.
     * @return true if symbol is found in hashmap, false otherwise.
     */
    public boolean contains(String symbol)
    {
        return symbolHashMap.containsKey(symbol);
    }

    /***
     * ACCESSOR: returns address in hashmap of given symbol.
     * PRECONDITION: symbol is in hahsmap (check w/ contains() first).
     * @param symbol  the key to look for in hashmap.
     * @return address associated with symbol in hashmap.
     */
    public int getAddress(String symbol)
    {
        return symbolHashMap.get(symbol);
    }


    /***
     * HELPER: Adds predefined symbols into hashmaps.
     * PRECONDITION: hashmap is initialized and symbols/values are valid.
     * POSTCONDITION: hashmp is filled with all predefined symbol.
     */
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

    /***
     * HELPER: adds KV pair Registers and address to hashmap.
     * PRECONDITION: hashmap is initialized.
     * POSTCONDITION: hashmap is filled with register/value pair.
     */
    private void addRegistersToTable()
    {
        for (int i = 0; i < 16; i++)
        {
            String key = "R" + i;
            symbolHashMap.put(key, i);
        }
    }


    /***
     * METHOD: describes the contents hashmap in formatted string.
     * PRECONDITION: hashmap is initialized.
     * @return a formmated string with all Key/Value pairs in hashmap.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("--- Symbol Table hashMap --\n");
        for (String key : symbolHashMap.keySet())
        {
            String str = String.format("%-5s | %s\n", key, symbolHashMap.get(key));
            sb.append(str);

        }
        sb.append("\n");

        return sb.toString();
    }

}
