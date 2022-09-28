import java.util.ArrayList;

public class functionASTnode extends node {

    private final String identifier ;
    private final ArrayList<variableNode> parameters ;
    private final ArrayList<variableNode> variables ;
    private final ArrayList<statementNode> statements ;

// Different constructor overloads depending on what type of function is returned by the FunctionDefinition function.
    public functionASTnode(String identifier, ArrayList<variableNode> parameters, ArrayList<variableNode> variables,ArrayList<statementNode> statements) {

        this.identifier = identifier;
        this.parameters = parameters;
        this.variables = variables;
        this.statements = statements ;
    }

    @Override
    public String toString() {

        return "FunctionASTnode(" + identifier + ", parameters: (" + parameters + "), variables: (" + variables + "), statements: (" + statements + ")";
    }
}
