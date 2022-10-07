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
             else if (tokenTemp.getType() == token.type.MINUS) {
                node = new mathOpNode(node, term(), mathOpNode.Op.SUBTRACT);
            }
        }
        return node ;
    }

    public node term() throws IOException {
        // TERM = FACTOR { (times or divide or modulo ) FACTOR}
        // Looks for what type of token it is to division or multiplication.
        node = factor();

        while (matchAndRemove(token.type.TIMES) || matchAndRemove(token.type.DIVIDE) || matchAndRemove(token.type.MODULO)) {

            if (tokenTemp.getType() == token.type.TIMES) {
                node = new mathOpNode(node, factor(), mathOpNode.Op.MULTIPLY);
            }
           else if (tokenTemp.getType() == token.type.DIVIDE) {
                node = new mathOpNode(node, factor(), mathOpNode.Op.DIVIDE);
            } else if (tokenTemp.getType() == token.type.MODULO) {
               node = new mathOpNode(node, factor(), mathOpNode.Op.MOD);
            }
        }
        return node ;
    }

    public node factor() throws IOException {
        // FACTOR = {-} number or lparen EXPRESSION rparen or variable
        if(matchAndRemove(token.type.NUMBER)) {

            return new floatNode(Float.parseFloat(tokenTemp.getValue()));

        } else if(matchAndRemove(token.type.LPAR)) {

            node = expression();
            matchAndRemove(token.type.RPAR);
            return node;
        } else if(matchAndRemove(token.type.identifier)) {

            return new variableReferenceNode(tokenTemp.getValue());
        } else {
// todo fix parenthesis not parsing correctly, and throwing exception even though it works perfectly fine.
            throw new IOException("Cannot create node correctly from bad input: " + node + ", " + tokenTemp);
        }
    }

    public boolean matchAndRemove(token.type tokentype) {

        // looks for a certain token given by input. If found, will display true and will be removed from list.
        try {
            if (tokenlist.get(0).getType().equals((tokentype))) {
                if(!tokenlist.get(0).getType().equals(token.type.EOL)) {
                    tokenTemp = tokenlist.get(0);
                }
                tokenlist.remove(0);
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {}
        return false;
    }
// New declarations for the buffers of the FunctionDefinition function.
    String identifier = null;
    ArrayList<variableNode> variables = new ArrayList<>() ;
    boolean variableFlag = false ;
// returns a functionASTnode by looking for the "define" keyword, then goes through the list of tokens to make sure
// everything is in order such as the parameters and the constants afterwards. Checks what type of function it is (no parameters, or no declarations, etc.)
    public functionASTNode functionDefinition() throws Exception {

        ArrayList<variableNode> parameter = new ArrayList<>();
        String functionName = null;
        variableNode.dataType dataTemp = null ;
        boolean parameterFlag = false ;
        ArrayList<statementNode> statements = new ArrayList<>();

        if(matchAndRemove(token.type.define)) {
            if(matchAndRemove(token.type.identifier)) {
                functionName = tokenTemp.getValue();

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
            } else {throw new Exception("Function declared wrong (name)."); }

        } if(variableFlag && parameterFlag){ return new functionASTNode(functionName,parameter,variables,statements);}
        else if(variableFlag && !parameterFlag){ return new functionASTNode(functionName,null,variables,statements);}
        else if(!variableFlag && parameterFlag) { return new functionASTNode(functionName,parameter,null,statements);}
        else if(!variableFlag && !parameterFlag) { return new functionASTNode(functionName,null,null,statements);}
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
                } else if (matchAndRemove(token.type.real)) {
                    variables.add(new variableNode(identifier, variableNode.dataType.REAL,false));
                }
            } else if(matchAndRemove(token.type.comma)) {
                List<token.type> tokenTypeList = tokenlist.stream().map(token::getType).collect(Collectors.toList());
                variableNode.dataType varType = null;

                if(tokenTypeList.contains(token.type.integer) && (tokenTypeList.get(tokenTypeList.indexOf(token.type.integer)+1)).equals(token.type.EOL)) {
                    varType = variableNode.dataType.INTEGER ;
                } else if(tokenTypeList.contains(token.type.real) && (tokenTypeList.get(tokenTypeList.indexOf(token.type.real)+1)).equals(token.type.EOL)) {
                    varType = variableNode.dataType.REAL ;
                }
                variables.add(new variableNode(identifier, varType,false));
                do {
                    matchAndRemove(token.type.identifier);
                    identifier = tokenTemp.getValue();
                    variables.add(new variableNode(identifier,varType,false));
                    matchAndRemove(token.type.comma);
                } while(!matchAndRemove(token.type.colon));

            }
        }
        matchAndRemove(token.type.integer);
        matchAndRemove(token.type.real);
        matchAndRemove(token.type.EOL);
        variableFlag = true ;
    }
// Processes the body, calls the statements function to process statements.
    public ArrayList<statementNode> processBody() throws IOException {
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
        try {
            return tokenlist.get(1).getType() == type;
        } catch (IndexOutOfBoundsException ignored) {}
        return false ;
    }
// Checks for an identifier token, assignment token, expression token, and an EOL to assign a variable or else it returns null if done incorrectly.
    public node assignment() throws IOException {
        matchAndRemove(token.type.EOL);
        if(matchAndRemove(token.type.identifier) && !peak(token.type.assign)) {
            identifier = tokenTemp.getValue();
            return new variableReferenceNode(identifier);
        }
            else if(matchAndRemove(token.type.assign)) {
                    return new assignmentNode(new variableReferenceNode(identifier), expression());
                }
            try {
                return new assignmentNode(new variableReferenceNode(identifier),parse());
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
        }

// Calls the statement function to process statements until it returns null and adds them to a list.
    public ArrayList<statementNode> statements() throws IOException {
        ArrayList<statementNode> statements = new ArrayList<>();
        while (statement()!=null) {
            statements.add((statementNode)statement());
        }
        return statements ;
    }
// Statement function returns assignment as a statement.
    public node statement() throws IOException {
        return assignment();
    }
// Creates a boolean expression node if the tokens (left expression, comparison, right expression) are given correctly.

    public booleanExpressionNode booleanExpression() throws IOException {
        node rightnode;
        booleanExpressionNode.compare comparer;
        node leftnode = expression();

        if(matchAndRemove(token.type.less)) {
            rightnode = expression();
            comparer = booleanExpressionNode.compare.lessThan ;

        } else if(matchAndRemove(token.type.equal)) {
            rightnode = expression();
            comparer = booleanExpressionNode.compare.Equals ;

        } else if(matchAndRemove(token.type.greater)) {
            rightnode = expression();
            comparer = booleanExpressionNode.compare.greater ;

        } else if(matchAndRemove(token.type.lequal)) {
            rightnode = expression();
            comparer = booleanExpressionNode.compare.lEquals ;

        } else if(matchAndRemove(token.type.gequal)) {
            rightnode = expression();
            comparer = booleanExpressionNode.compare.gEquals ;

        } else if(matchAndRemove(token.type.notequal)) {
            rightnode = expression();
            comparer = booleanExpressionNode.compare.notEqual ;
            
        } else {throw new IOException("cannot make boolean statement.");}
        
        return new booleanExpressionNode(leftnode,rightnode,comparer);
    }
    // Creates a while expression node if boolean expression is found and statements.
    public whileNode whileExpression() throws IOException {
        List<statementNode> statementList;
        booleanExpressionNode bool;

        if(matchAndRemove(token.type.whilee)) {
            bool = booleanExpression();
            matchAndRemove(token.type.EOL);
            statementList = statements();

        } else {throw new IOException("cannot make while statement.");}
        return new whileNode(bool,statementList);
    }
    // Creates a repeat expression node if boolean expression is found and statements.
    public repeatNode repeatExpression() throws IOException {
        List<statementNode> statementList;
        booleanExpressionNode bool;

        if(matchAndRemove(token.type.repeat)) {
            bool = booleanExpression();
            matchAndRemove(token.type.EOL);
            statementList = statements();

        } else {throw new IOException("cannot make repeat statement.");}
        return new repeatNode(bool,statementList);
    }
    // Creates a for expression node if there's a variable, start, end, and list of statements.
    public forNode forExpression() throws IOException {
        List<statementNode> statementList = null;
        node startNode = null ;
        node endNode = null ;
        variableReferenceNode var = null ;
        // What is meant here by start and end node ?? expression ?
        if(matchAndRemove(token.type.forr)) {
            if(matchAndRemove(token.type.identifier)) {
                var = new variableReferenceNode(tokenTemp.getValue());
                matchAndRemove(token.type.comma);
                startNode = expression();
                matchAndRemove(token.type.comma);
                endNode = expression();
                matchAndRemove(token.type.EOL);
                statementList = statements();
            }
        }
        return new forNode(var,startNode,endNode,statementList);
    }
    // Creates an if expression node if a boolean expression, statements, and possible other if nodes are found.
    public ifNode ifExpression() throws IOException {
        List<statementNode> statementList;
        booleanExpressionNode bool;
        ifNode ifNested = null ;
        elseNode elsee = null ;
// Perhaps could it be better to make a separate elsif method for recursion ?
        matchAndRemove(token.type.iff);
            bool = booleanExpression();
            matchAndRemove(token.type.EOL);
            statementList = statements();
            matchAndRemove(token.type.EOL);
            if(!(matchAndRemove(token.type.elsif) || matchAndRemove(token.type.elsee))) {
                return new ifNode(bool,statementList,ifNested,elsee);
            } else if(matchAndRemove(token.type.elsif)) {
                matchAndRemove(token.type.EOL);
                ifNested = ifExpression();
            } else if (matchAndRemove(token.type.elsee)) {
                matchAndRemove(token.type.EOL);
                List<statementNode> elseStatementList = statements();
                elsee = new elseNode(elseStatementList);
                return new ifNode(bool,statementList,ifNested,elsee);
            }
            return null ;
    }

}


