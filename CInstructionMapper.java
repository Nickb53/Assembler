import java.util.HashMap;

/**
 *  The CInstructionMapper contains HashMaps of the compCodes and the jumpCodes
 *  The key is the assembler version of the command, and the value is the binary representation
 *  we include the "a" bit within the comp code to make things cleaner
 */

public class CInstructionMapper {
    //hashMaps are used for storing Key, Value pairs for codes
    private HashMap<String, String> compCodes;
    private HashMap<String, String> jumpCodes;
    private HashMap<String, String> destCodes;

    /**
     * Constructor for the CInstructionMapper
     * Fills the compCodes, jumpCodes, and destCodes with our given binary values
     */
    public CInstructionMapper(){
        compCodes = new HashMap<>();
        compCodes.put("0", "0101010");
        compCodes.put("1","0111111");
        compCodes.put("-1","0111010");
        compCodes.put("D","0001100");
        compCodes.put("A","0110000");
        compCodes.put("!D","0001101");
        compCodes.put("!A","0110001");
        compCodes.put("-D","0001111");
        compCodes.put("-A","0110011");
        compCodes.put("D+1","0011111");
        compCodes.put("A+1","0110111");
        compCodes.put("D-1","0001110");
        compCodes.put("A-1","0110010");
        compCodes.put("D+A","0000010");
        compCodes.put("D-A","0010011");
        compCodes.put("A-D","0000111");
        compCodes.put("D&A","0000000");
        compCodes.put("D|A","0010101");

        //2nd column (a=1)
        compCodes.put("M","1110000");
        compCodes.put("!M","1110001");
        compCodes.put("-M","1110011");
        compCodes.put("M+1","1110111");
        compCodes.put("M-1","1110010");
        compCodes.put("D+M","1000010");
        compCodes.put("D-M","1010011");
        compCodes.put("M-D","1000111");
        compCodes.put("D&M","1000000");
        compCodes.put("D|M","1010101");

        //Adding Key Value pairs to jumpCodes hashMap
        jumpCodes = new HashMap<>();
        jumpCodes.put(null,"000");
        jumpCodes.put("JGT","001");
        jumpCodes.put("JEQ","010");
        jumpCodes.put("JGE","011");
        jumpCodes.put("JLT","100");
        jumpCodes.put("JNE","101");
        jumpCodes.put("JLE","110");
        jumpCodes.put("JMP","111");

        //adding destination codes to destCode hashmap
        destCodes = new HashMap<>();
        destCodes.put(null,"000");
        destCodes.put("M","001");
        destCodes.put("D","010");
        destCodes.put("MD","011");
        destCodes.put("A","100");
        destCodes.put("AM","101");
        destCodes.put("AD","110");
        destCodes.put("AMD","111");


    }

    /**
     * Method to get the compCode for a mnemonic
     * @param mnemonic the mnemonic to get a code for
     * @return the binary code
     */
    public String comp(String mnemonic){
        return compCodes.get(mnemonic);
        //If it exists, return binary string
        //else returns null
    }

    /**
     * Method to get the destCode for a mnemonic
     * @param mnemonic the mnemonic to get a code for
     * @return the binary code
     */
    public String dest(String mnemonic){
        return destCodes.get(mnemonic);
        //if there is a destination, return the code
        //otherwise the destination for null is 000, which goes nowhere
    }

    /**
     * Method to get the jump code for a mnemonic
     * @param mnemonic the mnemonic to get a code for
     * @return the binary code
     */
    public String jump(String mnemonic){
        return jumpCodes.get(mnemonic);
        //If it exists, otherwise the null code is 000 which means no jump
    }
}
