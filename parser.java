import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class parser {

    private List<token> tokenlist;
    private token tokenTemp ;
    private mathOpNode node;

    public parser(List<token> tokenlist) {
        this.tokenlist = tokenlist ;

    }

    public mathOpNode parse() throws IOException {

        matchAndRemove(token.type.EOL);
        expression();
        return this.node;

    }
    public mathOpNode expression() throws IOException {

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

            return new floatNode(Float.parseFloat(tokenTemp.getValue()));

        } else if(matchAndRemove(token.type.LPAR)) {

            node = expression();
            matchAndRemove(token.type.RPAR);
            // return (floatNode) node;
            return null ;
        } else {

            throw new IOException("Cannot create floatNode correctly from bad input (method factor() ).");
        }
    }

    public boolean matchAndRemove(token.type tokentype) {

        // looks for a certain token given by input. If found, will display true and will be removed from list.
        if (tokenlist.get(0).getType().equals((tokentype))) {
            tokenTemp = tokenlist.get(0) ;
            tokenlist.remove(0) ;
            return true;
        }
        return false;
    }
// New declarations for the buffers of the FunctionDefinition function.
    String identifier = null;
    float floats = 0;
    ArrayList<variableNode> variables = new ArrayList<>() ;
    boolean variableFlag = false ;
// returns a functionASTnode by looking for the define keyword, then goes through the list of tokens to make sure
// everything is in order such as the parameters and the constants afterwards. Checks what type of function it is (no parameters, or no declarations, etc.)
    public functionASTnode functionDefinition() throws Exception {

        ArrayList<variableNode> parameter = new ArrayList<>();
        String functionName = null;
        variableNode.dataType dataTemp = null ;
        boolean parameterFlag = false ;

        if(matchAndRemove(token.type.define)) {
            if(matchAndRemove(token.type.identifier)) {
                functionName = tokenTemp.getValue() ;

                if(matchAndRemove(token.type.LPAR)) {
                    while (!matchAndRemove(token.type.RPAR)) {

                        if(matchAndRemove(token.type.identifier)) {
                            identifier = tokenTemp.getValue();
                            parameterFlag = true ;
                            if(matchAndRemove(token.type.colon)) {
                                if(matchAndRemove(token.type.integer)) {
                                    dataTemp = variableNode.dataType.INTEGER ;
                                } else if(matchAndRemove(token.type.real)) {
                                    dataTemp = variableNode.dataType.REAL ;
                                }
                            } matchAndRemove(token.type.semicolon);
                          parameter.add(new variableNode(identifier,dataTemp,false));
                        }
                    } matchAndRemove(token.type.EOL);
                } if(matchAndRemove(token.type.constants)) {
                    processConstants();

                } if (matchAndRemove(token.type.variables)) {
                    processVariables();
                }

            } else {throw new Exception("Function declared wrong."); }
            processBody();

        } if(variableFlag && parameterFlag == true){ return new functionASTnode(functionName,parameter,variables);}
        else if(variableFlag == true && parameterFlag == false){ return new functionASTnode(functionName,null,variables);}
        else if(variableFlag == false && parameterFlag == true) { return new functionASTnode(functionName,parameter,null);}
        else if(variableFlag && parameterFlag == false) { return new functionASTnode(functionName,null,null);}
        else {throw new Exception("Function declared wrong.");}
    }
// Looks for EOLs, then finds the name and makes a new variableNode marked as a constant.
    public void processConstants() throws Exception {

        matchAndRemove(token.type.EOL);
        while (matchAndRemove(token.type.identifier)) {
            identifier = tokenTemp.getValue();
            if(matchAndRemove(token.type.equal)) {
                if(matchAndRemove(token.type.NUMBER)) {
                    floats = Float.parseFloat(tokenTemp.getValue());
                } else {throw new Exception("Constants declared wrong."); }
            } variableFlag = true ;
            variables.add(new variableNode(identifier,floats, variableNode.dataType.REAL,true));
        } matchAndRemove(token.type.EOL);

    }
// Looks for EOLs, then finds the name and its data type to create a new variableNode.
    public void processVariables() throws Exception {
        matchAndRemove(token.type.EOL);
        while(matchAndRemove(token.type.identifier)) {
            identifier = tokenTemp.getValue();
            if(matchAndRemove(token.type.colon)) {
                if(matchAndRemove(token.type.integer)) {
                    variables.add(new variableNode(identifier, variableNode.dataType.INTEGER,false));
                    variableFlag = true ;
                } else if (matchAndRemove(token.type.real)) {
                    variables.add(new variableNode(identifier, variableNode.dataType.REAL,false));
                    variableFlag = true ;
                }

            } else {throw new Exception("Variables declared wrong."); }
        }
        matchAndRemove(token.type.EOL);
    }
// Processes the body. Yet to be finished as per the assignment.
    public void processBody() {

        matchAndRemove(token.type.begin);
        matchAndRemove(token.type.EOL);
        matchAndRemove(token.type.end);
        matchAndRemove(token.type.EOL);
    }
}



