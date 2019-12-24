import java.io.*;

/**
 * The Assembler class takes in a assembly file and converts it into a binary .hack file
 */
public class Assembler {
    //Constant used for changing the input file
    public static final String FILE_PATH = "C:\\Users\\nicho\\Desktop\\Assembler\\src\\Pong.asm";

    public static void main(String[] args) {

        //Instantiating the symbolTable
        SymbolTable symbolTable = new SymbolTable();

        //the first pass takes care of all labels
        firstPass(FILE_PATH, symbolTable);

        //the second pass takes car of everything else
        //stores variables
        //uses the parser and CInstructionMapper to create the binary values of each command
        //writes the binary command to a .hack file
        secondPass(FILE_PATH, symbolTable, "Pong.hack");

    }

    /**
     * First pass takes care of labels, adds the label name and value to the SymbolTable
     * @param assemblyFileName the file we are parsing
     * @param symbolTable the SymbolTable to add the label to
     */
    private static void firstPass(String assemblyFileName, SymbolTable symbolTable){
        Parser parser = new Parser(assemblyFileName);
        while(parser.hasMoreCommands()){
            parser.advance();
            if(parser.getCommandType() == Command.L_COMMAND) {
                symbolTable.addEntry(parser.getSymbol(), parser.getLineNumber());
            }
        }
    }

    /**
     *  The second pass goes through the assembly again, assigning variables to registers and
     *  writes the binary code to a .hack file
     * @param assemblyFileName the file we are converting
     * @param symbolTable the symbol map for our variables, and loops
     * @param binaryFileName the output .hack file
     */
    private static void secondPass(String assemblyFileName, SymbolTable symbolTable, String binaryFileName){
        //creating a parser to read each line of assembly
        Parser parser = new Parser(assemblyFileName);
        //mapper is used for instruction codes
        CInstructionMapper mapper = new CInstructionMapper();

        //index for storing variables, starting at register 16
        int varIndex = 16;

        //attempting to open the file to write to (creates on if it doesnt exist)
        try {
            PrintWriter out = new PrintWriter(binaryFileName);
        //GO through each command in the assembly file
        while(parser.hasMoreCommands()){

            //create a new stringbuilder for C commands
            StringBuilder builder = new StringBuilder();
            parser.advance();

            //If it is an A command:
            if(parser.getCommandType() == Command.A_COMMAND){

                //Is this a number?
                if(parser.getSymbol().matches("[0-9]+")){
                    out.println(decimalToBinary(Integer.parseInt(parser.getSymbol())));
                    continue;
                }

                //if it isnt a number it is a variable
                //search the table to see if the variable is already there
                if(symbolTable.contains(parser.getSymbol())){
                    //add the translation to the binary file
                   out.println(decimalToBinary(symbolTable.getAddress(parser.getSymbol())));
                }

                //if it is not stored, add it
                else{
                    //System.out.println("Adding: "+ parser.getSymbol() + " to " + varIndex);
                    symbolTable.addEntry(parser.getSymbol(), varIndex);
                    //add the translation to the binary file
                    out.println(decimalToBinary(varIndex));
                    varIndex++;
                }
            }
            //If there is a C command:
            if(parser.getCommandType() == Command.C_COMMAND){
                //use the builder and CInstructionMapper to build the binary value
                builder.append("111");
                builder.append(mapper.comp(parser.getCompMnemonic()));
                builder.append(mapper.dest(parser.getDestMnemonic()));
                builder.append(mapper.jump(parser.getJumpMnemonic()));
                out.println(builder);
            }


        }
            //close the file we are done writing
            out.close();
        } catch (IOException e) {
        e.printStackTrace();
    }
        //End of the loop, and secondPass method
    }

    /**
     * In case an invalid syntax is found
     * @param message
     */
    private static void handleError(String message){
        System.out.println(message);
    }

    /**
     * Converts a decimal number to binary using the Integer wrapper class
     * Also formats the binary number to 16 bit binary
     * @param number the decimal value
     * @return the binary value
     */
    private static String decimalToBinary(int number){
        return String.format("%16s", Integer.toBinaryString(number)).replace(' ', '0');
    }
}
