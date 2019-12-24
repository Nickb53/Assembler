import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  Parser.java is used to read in a line of assembly and rip it apart.
 *  First it removes any uneccessary whitespace, and any comments
 *  Then it looks to see which type of command it is
 *  Depending on the command, the instance variables are updated
 */
public class Parser {
    //instance variables
    private String rawLine;
    private String cleanLine;
    private Scanner inputFile;
    private Command commandType;
    private String compMnemonic;
    private String destMnemonic;
    private String jumpMnemonic;
    private int lineNumber;
    private String symbol;

    /**
     * Contstructor for the parse, opens a file to parse
     * @param fileName the file to be parsed
     */
    public Parser(String fileName){
        //attempt to open the file
        try {
            inputFile = new Scanner(new File(fileName));
        }catch (FileNotFoundException e){
            System.err.println("Error opening file " + fileName);
            System.exit(0);
        }
    }
    /**
     * Method to advance the parser to the next line to parse
     * Advances to the next line, and also cleans the line
     */
    public void advance(){
        rawLine = inputFile.nextLine();
        cleanLine = getCleanLine(rawLine);

        if(!(cleanLine.length() == 0))
            parse();
        if(commandType != Command.L_COMMAND && !(cleanLine.equals(""))){
            lineNumber++;
        }
    }

    /**
     * Method to clean a raw line from the assembly file
     * The line needs to be cleaned before it can be translated into binary
     * @return the cleaned line
     */
    public String getCleanLine(String line){
        //If the line is empty
        if(line.length() == 0) return "";

        //Otherwise clean the line
        //Remove any comments and excess whitespace
        //I.E. ("M=D+M;JMP   //comment") will convert to ("M=D+M;JMP")

        cleanLine = rawLine;                     //"M=D+M;JMP   //comment"


        int index = cleanLine.indexOf("//");
        cleanLine = (index != -1) ?
                cleanLine.substring(0, index).trim() : cleanLine.replaceAll("\\s","");
        //System.out.println(cleanLine);
        return cleanLine;
    }

    /**
     * @return the type of command
     */
    public Command getCommandType(){
        return commandType;
    }

    /**
     * @return the comp Mnemonic
     */
    public String getCompMnemonic(){
        return compMnemonic;
    }

    /**
     * @return the dest Mnemonic
     */
    public String getDestMnemonic(){
        return destMnemonic;
    }

    /**
     * @return the jump Mnemonic
     */
    public String getJumpMnemonic(){
        return jumpMnemonic;
    }

    /**
     * @return the line number
     */
    public int getLineNumber(){
        return lineNumber;
    }

    /**
     * @return the raw line of assembly code
     */
    public String getRawLine(){
        return rawLine;
    }

    /**
     * @return the symbol in focus
     */
    public String getSymbol(){
        return symbol;
    }

    /**
     * Checks to see if there are more commands to parse
     * @return if there are more commands
     */
    public boolean hasMoreCommands(){
        return inputFile.hasNextLine();
    }

    /**
     * This method parses the cleaned line of assembly
     * It looks at the type of command being executed and calls the corresponding parsing helper methods
     */
    private void parse(){
        parseCommandType();
        if(getCommandType() == Command.A_COMMAND)
            parseSymbol();
        if(getCommandType() == Command.L_COMMAND)
            parseSymbol();
        if(getCommandType() == Command.C_COMMAND) {
            parseComp();
            parseDest();
            parseJump();
        }
    }

    /**
     * This method looks at the first character to determine the type of command being used
     */
    private void parseCommandType(){

        //A commands start with @
        if(cleanLine.charAt(0) == '@') {
            commandType = Command.A_COMMAND;
        }

        //L commands start with (
        if(cleanLine.charAt(0) == '(') {
            commandType = Command.L_COMMAND;
        }

        //null means no command
        if(cleanLine.equals(null)) {
            commandType = Command.NO_COMMAND;
        }

        //anything else is a C command
        else if (!(cleanLine.charAt(0) == '@') && !(cleanLine.charAt(0) == '(')){
            commandType = Command.C_COMMAND;
        }

    }

    /**
     * This method parses the comp portion of a C command
     */
    private void parseComp(){
        //for tracking startind and stopping points of the command
        int start = 0;
        int end = cleanLine.length();

        //modifying the starting and stopping points depending on other parts of the command
        if(cleanLine.contains("="))
            start = cleanLine.indexOf('=') + 1;
        if(cleanLine.contains(";"))
            end = cleanLine.indexOf(';');
        compMnemonic = cleanLine.substring(start, end);
    }

    /**
     * This method parses the Dest portion of a C command
     */
    private void parseDest(){
        //If the line has no =, there is no destination
        if(!(cleanLine.contains("=")))
            destMnemonic = null;

        else if (cleanLine.contains("="))
            destMnemonic = cleanLine.substring(0,cleanLine.indexOf('='));
    }

    /**
     * This method parses the jump portion of a C command
     */
    private void parseJump(){
        //if there is no ; then there is no Jump command
        if(!(cleanLine.contains(";")))
            jumpMnemonic = null;

        else if(cleanLine.contains(";"))
            jumpMnemonic = cleanLine.substring(cleanLine.indexOf(';')+1, cleanLine.length());
    }

    /**
     * This method parses symbols for A commands and L commands
     */
    private void parseSymbol(){
        //for labels
        if(cleanLine.charAt(0) == '('){
            symbol = cleanLine.substring(1, cleanLine.length() - 1);
        }
        //for @ commands
        else if (!(cleanLine.charAt(0) == '('))
            symbol = cleanLine.substring(1,cleanLine.length());
    }

    //toString method (for testing purposes)
    @Override
    public String toString() {
        return "Parser{" +
                ", cleanLine='" + cleanLine + '\'' +
                ", commandType=" + commandType +
                ", compMnemonic='" + compMnemonic + '\'' +
                ", destMnemonic='" + destMnemonic + '\'' +
                ", jumpMnemonic='" + jumpMnemonic + '\'' +
                ", lineNumber=" + lineNumber +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}











