import java.util.ArrayList;

public class functionASTnode extends node {

    private String identifier ;
    private ArrayList<variableNode> parameters ;
    private ArrayList<variableNode> variables ;
// Different constructor overloads depending on what type of function is returned by the FunctionDefinition function.
    public functionASTnode(String identifier, ArrayList<variableNode> parameters, ArrayList<variableNode> variables) {

        this.identifier = identifier;
        this.parameters = parameters;
        this.variables = variables;
    }

    @Override
    public String toString() {

        return "FunctionASTnode(" + identifier + ", parameters: (" + parameters + "), variables: (" + variables + ")" ;
    }
}
