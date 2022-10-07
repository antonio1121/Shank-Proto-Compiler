import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class shank {

    public static void main(String[] args) throws Exception {
// checks if the arguments are put in correctly.
        if (args.length != 1) {
            throw new IOException("Please put in the correct arguments (one file name.) ");
        }

        Path filePath = Paths.get(args[0]);

        List<token> tokenReturnList;

        try {
// creates a List of lines of the file given, then is run through the lexer method for lexical analysis using a loop.
            List<String> lines = Files.readAllLines(filePath);

            lexer lexing  = new lexer();

            for(String line: lines) {

                lexing.lex(line);
            }
            tokenReturnList = lexing.tokenlist ;
            System.out.println("BUGS: Sometimes expressions don't parse if there are no spaces in between numbers and operands.");
            System.out.println("Factor method throws exception even though it works correctly ?!?! ");
            System.out.println();
            System.out.println(tokenReturnList);
            System.out.println();
// creates a new parser.
            parser parsing = new parser(tokenReturnList);

         //System.out.println(parsing.parse().toString());

           System.out.println(parsing.functionDefinition());

// checks if the filename given is correct.
        } catch (FileNotFoundException e) {
            System.out.printf("The file could not be found.%n" + e);
        }
    }
}
