import java.util.HashMap;
import java.util.List;

public class semanticAnalysis {
// Breaks down a function into various statements so that each statement, including looping ones,
// can be checked for correct type checking.
    public static void checkAssignments(HashMap<String,callableNode> functions) throws Exception {

        for(callableNode function: functions.values()) {

            if (function instanceof functionASTNode) {
                List<statementNode> statements = ((functionASTNode) function).getStatements();

                for (statementNode statement : statements) {

                    if(statement instanceof forNode) {
                        List<statementNode> forStatements = ((forNode) statement).getStatements();
                        for(statementNode forStatement : forStatements) {
                            checkAssignment(forStatement);
                        }
                    } else if (statement instanceof ifNode) {
                        List<statementNode> ifStatements = ((ifNode) statement).getStatementList();
                        for(statementNode ifStatement : ifStatements) {
                            checkAssignment(ifStatement);
                        }
                        if(((ifNode) statement).getIfNested()!=null) {
                            List<ifNode> ifNestedStatements = ((ifNode) statement).getIfNested();
                            for(ifNode ifNestedStatement : ifNestedStatements) {
                                List<statementNode> ifNestedStatementList = ifNestedStatement.getStatementList();
                                for(statementNode ifNestedStatementListItem : ifNestedStatementList) {
                                    checkAssignment(ifNestedStatementListItem);
                                }
                            }
                        }
                    } else if (statement instanceof repeatNode) {
                        List<statementNode> repeatStatements = ((repeatNode) statement).getStatements();
                        for(statementNode repeatStatement : repeatStatements) {
                            checkAssignment(repeatStatement);
                        }

                    } else if(statement instanceof whileNode) {
                        List<statementNode> whileStatements = ((whileNode) statement).getStatements();
                        for(statementNode whileStatement : whileStatements) {
                            checkAssignment(whileStatement);
                        }
                    } else if(statement instanceof assignmentNode) {
                        checkAssignment(statement);
                    }
                }
            }
        }

    }
// Checks an individual assignment.
    public static void checkAssignment(statementNode statement) throws Exception {

        if(statement instanceof assignmentNode) {
            variableReferenceNode vrn = ((assignmentNode) statement).getVrn();
            node expression = ((assignmentNode) statement).getExpression();
            if(vrn.isInt()&&!(expression instanceof integerNode)) {
                throw new Exception("The expression of this variable must be an integer.");
            } else if(!vrn.isInt()&&(expression instanceof integerNode)) {
                throw new Exception("The variable is not an integer, but the expression is.");
            }
        }
    }
}
