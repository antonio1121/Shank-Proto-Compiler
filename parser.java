import java.io.IOException;
import java.util.List;

public class parser {

    private List<token> tokenlist;

    public parser(List<token> tokenlist) {
        this.tokenlist = tokenlist ;

    }

    public node parse() throws IOException {

        matchAndRemove(token.type.EOL);
        return expression();

    }
    public node expression() throws IOException {

        // EXPRESSION = TERM { (plus or minus) TERM}
        // Looks for what type of token it is to addition or subtraction.
        if (matchAndRemove(token.type.PLUS)) {

            return new mathOpNode(term(),term(),mathOpNode.Op.ADD);

        } else if (matchAndRemove(token.type.MINUS)) {

            return new mathOpNode(term(),term(), mathOpNode.Op.SUBTRACT);

        } else {
            term();
         //   throw new IOException("Cannot create mathOpNode correctly from bad input (method expression() ).");
        }

        return null;
    }

    public node term() throws IOException {
        // TERM = FACTOR { (times or divide) FACTOR}
        // Looks for what type of token it is to division or multiplication.
        if (matchAndRemove(token.type.TIMES)) {

            return new mathOpNode(factor(),factor(),mathOpNode.Op.MULTIPLY) ;

        } else if (matchAndRemove(token.type.DIVIDE)) {

            return new mathOpNode(factor(),factor(),mathOpNode.Op.DIVIDE) ;

        } else {
            factor();
           // throw new IOException("Cannot create mathOpNode correctly from bad input (method term() ).");
        }

        return null;
    }

    public floatNode factor() throws IOException {
        // FACTOR = {-} number or lparen EXPRESSION rparen
        if(matchAndRemove(token.type.NUMBER)) {

            return new floatNode(Float.parseFloat(tokenlist.get(0).getValue())); // takes next value instead of current number thts why it crashes

        } else if(matchAndRemove(token.type.LPAR)) {

                    return (floatNode) expression();
        } else {

            throw new IOException("Cannot create floatNode correctly from bad input (method factor() ).");
        }
    }

    public boolean matchAndRemove(token.type tokentype) {

        // looks for a certain token given by input. If found, will display true and will be removed from list.
        if (tokenlist.get(0).getType().equals((tokentype))) {
                tokenlist.remove(0);
                return true;
            }
        return false;
    }
}



