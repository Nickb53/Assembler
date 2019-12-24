import java.util.HashMap;

/**
 * The SymbolTable class takes care of our variable names in the assembly language
 * it determines if the symbols being used are valid
 */
public class SymbolTable {

    //Constants used for storing valid characters for variable names
    private static final String ALL_VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNNOPQRSTUVWXYZ_$0123456789.:";
    private static final String INITIAL_VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_$";
    private HashMap<String, Integer> symbolTable;

    /**
     * Constructor for the SymbolTable
     * Generates with the default symbols used for our assembly language
     * R0 -> R15 , SCREEN, and KBD (Keyboard)
     */
    public SymbolTable(){
    symbolTable = new HashMap<>();
    symbolTable.put("R0",0);
    symbolTable.put("R1",1);
    symbolTable.put("R2",2);
    symbolTable.put("R3",3);
    symbolTable.put("R4",4);
    symbolTable.put("R5",5);
    symbolTable.put("R6",6);
    symbolTable.put("R7",7);
    symbolTable.put("R8",8);
    symbolTable.put("R9",9);
    symbolTable.put("R10",10);
    symbolTable.put("R11",11);
    symbolTable.put("R12",12);
    symbolTable.put("R13",13);
    symbolTable.put("R14",14);
    symbolTable.put("R15",15);
    symbolTable.put("SCREEN",16384);
    symbolTable.put("KDB",24576);
    symbolTable.put("SP",0);
    symbolTable.put("LCL",1);
    symbolTable.put("ARG",2);
    symbolTable.put("THIS",3);
    symbolTable.put("THAT",4);
}

    /**
     * Method to add an entry to the SymbolTable
     * Checks to see if the name is valid, and if it already is in the table
     * @param symbol the symbol to add
     * @param address the address to add it to
     * @return if the symbol was successfully added or not
     */
    public boolean addEntry(String symbol, int address){
    //If the name is invalid return false
    if(!validName(symbol))
        return false;
    //if the table already contains the value, return false
    if(symbolTable.containsKey(symbol))
        return false;
    //otherwise add the new symbol and return true
    symbolTable.put(symbol, address);
    return true;

}

    /**
     * Method to check if the SymbolTable contains a symbol
     * @param symbol the symbol to check
     * @return if the table contains the symbol or not
     */
    public boolean contains(String symbol){
    return symbolTable.containsKey(symbol);
}

    /**
     * This method returns the address of a symbol in the table
     * @param symbol the symbol we are finding the address for
     * @return the address
     */
    public int getAddress(String symbol){
    return symbolTable.get(symbol);
}

    /**
     * Method used to check if a symbol name is valid
     * @param symbol the name to check
     * @return if the name is valid or not
     */
    public boolean validName(String symbol){
    int length = symbol.length();

    //Test if the first character is valid
    String firstCharacter = symbol.substring(0,1);
    if(!INITIAL_VALID_CHARS.contains(firstCharacter)){
        return false;
    }

    //If the first character is valid, then check the rest of them
    for(int i = 1; i < length; i++){
        String focusCharacter = symbol.substring(i,i+1);
        if(! ALL_VALID_CHARS.contains(focusCharacter)){
            return false;
        }
    }
    return true;
}

}
