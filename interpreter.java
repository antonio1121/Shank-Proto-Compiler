import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class interpreter {
// The list of all variables stored here.
    static HashMap<String, interpreterDataType> variableList = new HashMap<>();


    public interpreter() {

    }
// Resolves an expression.
    public static float resolve(node node) {

        if (node instanceof floatNode) {
            return ((floatNode) node).getFloatNumber();

        } else if (node instanceof integerNode) {
            return (((integerNode) node).getIntNumber());

        }else if(node instanceof variableReferenceNode) {
            return Float.parseFloat(variableList.get(((variableReferenceNode) node).getName()).toString());

        } else if (node instanceof mathOpNode) {

            float left = resolve(((mathOpNode) node).getLeft());
            float right = resolve(((mathOpNode) node).getRight());

            if(((mathOpNode) node).getOp().equals(mathOpNode.Op.ADD)) {
                return left + right ;
            } else if(((mathOpNode) node).getOp().equals(mathOpNode.Op.SUBTRACT)) {
                return left - right ;
            } else if(((mathOpNode) node).getOp().equals(mathOpNode.Op.MULTIPLY)) {
                return left * right ;
            } else if(((mathOpNode) node).getOp().equals(mathOpNode.Op.DIVIDE)) {
                return left / right ;
            } else if(((mathOpNode) node).getOp().equals(mathOpNode.Op.MOD)) {
                return left % right ;
            }
        }
        return 0 ;
    }
    // resolves a String.
    public static String resolveString(node node) {
        if(node instanceof stringNode) {
            return node.toString();
        } else if(node instanceof mathOpNode) {
            if(((mathOpNode) node).getLeft() instanceof stringNode && (((mathOpNode) node).getRight() instanceof stringNode)) {
                return ((mathOpNode) node).getLeft().toString() + ((mathOpNode) node).getRight().toString();
            } else if(((mathOpNode) node).getLeft() instanceof stringNode && (((mathOpNode) node).getRight() instanceof charNode)) {
                return ((mathOpNode) node).getLeft().toString() + ((mathOpNode) node).getRight().toString();
            } else if (((mathOpNode) node).getLeft() instanceof charNode && (((mathOpNode) node).getRight() instanceof charNode)) {
                return ((mathOpNode) node).getLeft().toString() + ((mathOpNode) node).getRight().toString();
            }
        }
        return null ;
    }
    // resolves a boolean statement.
    public static boolean resolveBoolean(node node) {
        if(node instanceof boolNode) {
            return Boolean.parseBoolean(node.toString());
        } else if(node instanceof booleanExpressionNode) {
            return processBoolean((booleanExpressionNode) node);
        }
        return false ;
    }
    // resolves a character.
    public static char resolveCharacter(node node) {
        if(node instanceof charNode) {
            return node.toString().charAt(0);
        }
        return '0' ;
    }

// Interprets a function by checking if it is a BIF or a user defined one, and runs the parameters through it.
    public static void interpretFunction(functionASTNode function, List<interpreterDataType> parameters) throws Exception {
        int i = 0;

        for (interpreterDataType parameter : parameters) {
            variableList.put(function.getParameters().get(i).toString(), parameter);
            i++;
        }

        for (variableNode variable : function.getVariables()) {
            variableList.put(variable.getIdentifier(), new floatDataType(Float.parseFloat(variable.getValue().toString())));
        }

        interpretBlock(function.getStatements());
    }
// Processes statements within a function.
    public static void interpretBlock(List<statementNode> statements) throws Exception {
        for (statementNode statement : statements) {
            if (statement instanceof functionCallNode) {
                if(shank.functionList.containsKey(((functionCallNode) statement).getIdentifier())) {
                    List<interpreterDataType> values = new ArrayList<>();
                    //todo what is values supposed to do? list is updated, but never queried.
                    for(parameterNode item : ((functionCallNode) statement).getArgs()) {

                        if(item.getParameter() instanceof integerNode) {
                            values.add(new intDataType(((integerNode) item.getParameter()).getIntNumber()));

                        } else if(item.getParameter() instanceof floatNode) {
                            values.add(new floatDataType(((floatNode) item.getParameter()).getFloatNumber()));

                        } else {
                            values.add(variableList.get(item.toString()));
                        }
                    }
                    System.out.println(values);
                } else {throw new Exception("Function does not exist.");}
// Processes assignments,if,while,repeat,and for statements, and if found, and put its on the hash map.
            } else if(statement instanceof assignmentNode) {
                if(((assignmentNode) statement).getVrn().isInt()) {
                    variableList.put(((assignmentNode) statement).getVrn().getName(),new intDataType((int) resolve(((assignmentNode) statement).getExpression())));
                } else {
                    variableList.put(((assignmentNode) statement).getVrn().getName(),new floatDataType(resolve(((assignmentNode) statement).getExpression())));
                }
            } else if(statement instanceof ifNode) {
                if(processBoolean(((ifNode) statement).getBool())) {
                    interpretBlock(((ifNode) statement).getStatementList());
                }
                for(ifNode ifStatements: ((ifNode) statement).getIfNested()) {
                    if(processBoolean(ifStatements.getBool())) {
                        interpretBlock(ifStatements.getStatementList());
                    }
                }
                if(!processBoolean(((ifNode) statement).getBool())) {
                    interpretBlock(((ifNode) statement).getElsee().getStatementList());
                }

            } else if(statement instanceof whileNode) {
                if(processBoolean(((whileNode) statement).getBool())) {
                    interpretBlock(((whileNode) statement).getStatements());
                }

            } else if(statement instanceof repeatNode) {
                if(processBoolean(((repeatNode) statement).getBool())) {
                    interpretBlock(((repeatNode) statement).getStatements());
                }

            }else if(statement instanceof forNode) {
                if(((forNode) statement).getStartNode() instanceof integerNode && ((forNode) statement).getEndNode() instanceof integerNode) {
                    int start = ((integerNode) ((forNode) statement).getStartNode()).getIntNumber();
                    int end = ((integerNode) ((forNode) statement).getEndNode()).getIntNumber();

                    while(start!=end) {
                        interpretBlock(((forNode) statement).getStatements());
                        start++;
                    }
                } else {throw new Exception("Indexers are not integers.");}
            }

        }

    }
    // processes booleans in a method to avoid redundant boolean checking code.
    public static boolean processBoolean(booleanExpressionNode node) {

        float left = resolve(node.getLeftExpression());
        float right = resolve(node.getRightExpression());

        if(node.getCompare().equals(booleanExpressionNode.compare.greater)) {
            return left > right ;
        } else if (node.getCompare().equals(booleanExpressionNode.compare.lessThan)) {
            return left < right ;
        } else if (node.getCompare().equals(booleanExpressionNode.compare.gEquals)) {
            return left >= right ;
        } else if (node.getCompare().equals(booleanExpressionNode.compare.lEquals)) {
            return left <= right ;
        } else if (node.getCompare().equals(booleanExpressionNode.compare.Equals)) {
            return left == right ;
        } else if(node.getCompare().equals(booleanExpressionNode.compare.notEqual)) {
            return left != right ;
        }
        return false ;
    }
}

