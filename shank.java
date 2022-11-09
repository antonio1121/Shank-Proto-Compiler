import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class shank {

    public static HashMap<String,callableNode> functionList = new HashMap<>();

    public static void main(String[] args) throws Exception {
// checks if the arguments are put in correctly.
        if (args.length != 1) {
            throw new IOException("Please put in the correct arguments (one file name.) ");
        }

        Path filePath = Paths.get(args[0]);

        List<token> tokenReturnList;

        List<String> lines = new ArrayList<>();
        try {
// creates a List of lines of the file given, then is run through the lexer method for lexical analysis using a loop.
            lines = Files.readAllLines(filePath);
        } catch (FileNotFoundException e) {
            System.out.printf("The file could not be found.%n" + e);
        } // checks if the filename given is correct.

        lexer lexing = new lexer();

        for (String line : lines) {

            lexing.lex(line);
        }
        tokenReturnList = lexing.tokenlist;
        System.out.println("BUGS: Sometimes expressions don't parse if there are no spaces in between numbers, operands, and parenthesis.");
        System.out.println("Factor method throws exception even though it works correctly ?!?! ");
        System.out.println();
        System.out.println(tokenReturnList);
        System.out.println();
// creates a new parser.
        parser parsing = new parser(tokenReturnList);

        functionList.put("getRandom",new getRandom());
        functionList.put("integerToReal",new integerToReal());
        functionList.put("read",new read());
        functionList.put("realToInteger", realToInteger.getInstance());
        functionList.put("squareRoot", new squareRoot());
        functionList.put("write", new write());

        while(parsing.functionDefinition()!=null) {
            functionASTNode function = parsing.functionDefinition();
            functionList.put(function.getIdentifier(),function);
        }
        if(!functionList.containsKey("start")) {
            throw new Exception("You must have a start function to run.");
        }

        semanticAnalysis.checkAssignments(functionList);
        interpreter interpret = new interpreter();


        interpreter.interpretFunction((functionASTNode) functionList.get("start"),null);
        System.out.println(interpreter.variableList);




    }
}
