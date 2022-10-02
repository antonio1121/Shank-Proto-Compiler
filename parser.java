import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class parser {

    private final List<token> tokenlist;
    private token tokenTemp ;
    private node node;

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
        node = term();

        while (matchAndRemove(token.type.PLUS) || matchAndRemove(token.type.MINUS)) {

            if (tokenTemp.getType() == token.type.PLUS) {
                node = new mathOpNode(node, term(), mathOpNode.Op.ADD);
            }
            if (tokenTemp.getType() == token.type.MINUS) {
                node = new mathOpNode(node, term(), mathOpNode.Op.SUBTRACT);
            }
        }
        return node ;
    }

    public node term() throws IOException {
        // TERM = FACTOR { (times or divide) FACTOR}
        // Looks for what type of token it is to division or multiplication.
        node = factor();

        while (matchAndRemove(token.type.TIMES) || matchAndRemove(token.type.DIVIDE)) {

            if (tokenTemp.getType() == token.type.TIMES) {
                node = new mathOpNode(node, factor(), mathOpNode.Op.MULTIPLY);
            }
            if (tokenTemp.getType() == token.type.DIVIDE) {
                node = new mathOpNode(node, factor(), mathOpNode.Op.DIVIDE);
            }
        }
        return node ;
    }

    public floatNode factor() throws IOException {
        // FACTOR = {-} number or lparen EXPRESSION rparen
        if(matchAndRemove(token.type.NUMBER)) {

            return new floatNode(Float.parseFloat(tokenTemp.getValue()));

        } else if(matchAndRemove(token.type.LPAR)) {

            node = expression();
            matchAndRemove(token.type.RPAR);
            return (floatNode) node;
        } else {
// todo fix parenthesis not parsing correctly
            throw new IOException("Cannot create node correctly from bad input (method factor() ).");
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
    ArrayList<variableNode> variables = new ArrayList<>() ;
    boolean variableFlag = false ;
// returns a functionASTnode by looking for the define keyword, then goes through the list of tokens to make sure
// everything is in order such as the parameters and the constants afterwards. Checks what type of function it is (no parameters, or no declarations, etc.)
    public functionASTnode functionDefinition() throws Exception {

        ArrayList<variableNode> parameter = new ArrayList<>();
        String functionName = null;
        variableNode.dataType dataTemp = null ;
        boolean parameterFlag = false ;
        ArrayList<statementNode> statements = new ArrayList<>();

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
                    variableFlag = true ;
                    processVariables();
                }
            statements = processBody();
            } else {throw new Exception("Function declared wrong."); }

        } if(variableFlag && parameterFlag){ return new functionASTnode(functionName,parameter,variables,statements);}
        else if(variableFlag && !parameterFlag){ return new functionASTnode(functionName,null,variables,statements);}
        else if(!variableFlag && parameterFlag) { return new functionASTnode(functionName,parameter,null,statements);}
        else if(!variableFlag && !parameterFlag) { return new functionASTnode(functionName,null,null,statements);}
        else {throw new Exception("Function declared wrong.");}
    }
// Looks for EOLs, then finds the name and makes a new variableNode marked as a constant.
    public void processConstants() throws Exception {

        matchAndRemove(token.type.EOL);
        while (matchAndRemove(token.type.identifier)) {
            identifier = tokenTemp.getValue();
            if(matchAndRemove(token.type.equal)) {
                if(matchAndRemove(token.type.NUMBER)) {
                    variables.add(new variableNode(identifier,new floatNode(Float.parseFloat(tokenTemp.getValue())), variableNode.dataType.REAL,true));
                    variableFlag = true ;
                } else {throw new Exception("Constants declared wrong."); }
            }
        } matchAndRemove(token.type.EOL);

    }
// Looks for EOLs, then finds the name and its data type to create a new variableNode.
    public void processVariables() {
        matchAndRemove(token.type.EOL);
        if(matchAndRemove(token.type.identifier)) {
            identifier = tokenTemp.getValue();
            if(matchAndRemove(token.type.colon)) {
                if(matchAndRemove(token.type.integer)) {
                    variables.add(new variableNode(identifier, variableNode.dataType.INTEGER,false));
                    variableFlag = true ;  // todo fix... somehow to accept multiple variables
                } else if (matchAndRemove(token.type.real)) {
                    variables.add(new variableNode(identifier, variableNode.dataType.REAL,false));
                    variableFlag = true ;
                }

            }
        } if (matchAndRemove(token.type.comma)) {
            List<token.type> tokenTypeList = tokenlist.stream().map(token::getType).collect(Collectors.toList());

            do {
                if(matchAndRemove(token.type.identifier)) {
                    identifier = tokenTemp.getValue();
                    if(tokenTypeList.contains(token.type.real)) {
                        variables.add(new variableNode(identifier, variableNode.dataType.REAL,false));
                    } else if(tokenTypeList.contains(token.type.integer)) {
                        variables.add(new variableNode(identifier, variableNode.dataType.INTEGER,false));
                    }
                    matchAndRemove(token.type.comma);
                    // todo ended here look for future token of integer or real. Statements will not work until finished.
                }
            } while(!matchAndRemove(token.type.colon));
        }
        matchAndRemove(token.type.EOL);
    }
// Processes the body, calls the statements function to process statements.
    public ArrayList<statementNode> processBody() {
        ArrayList<statementNode> statements ;
        matchAndRemove(token.type.begin);
        matchAndRemove(token.type.EOL);
        statements = statements();
        matchAndRemove(token.type.end);
        matchAndRemove(token.type.EOL);
        return statements;
    }
// The peak method to see future tokens.
    public boolean peak(token.type type) {

        return tokenlist.get(1).getType() == type;
    }
// Checks for an identifier token, assignment token, expression token, and an EOL to assign a variable or else it returns null if done incorrectly.
    public node assignment() {
        matchAndRemove(token.type.EOL);
        if(matchAndRemove(token.type.identifier) && !peak(token.type.assign)) {
            identifier = tokenTemp.getValue();
            return new variableReferenceNode(identifier);
        }
            else if(matchAndRemove(token.type.assign)) {
                matchAndRemove(token.type.EOL);
                if(matchAndRemove(token.type.NUMBER)) {
                    return new assignmentNode(new variableReferenceNode(identifier), new mathOpNode(new floatNode(Float.parseFloat(tokenTemp.getValue())),null,null));
                }
            try {
                return new assignmentNode(new variableReferenceNode(identifier),parse());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
        }

// Calls the statement function to process statements until it returns null and adds them to a list.
    public ArrayList<statementNode> statements() {
        ArrayList<statementNode> statements = new ArrayList<>();
        while (statement()!=null) {
            statements.add((statementNode)statement());
        }
        return statements ;
    }
// Statement function returns assignment as a statement.
    public node statement() {
        return assignment();
    }

}


