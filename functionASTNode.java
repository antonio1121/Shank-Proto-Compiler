import java.util.ArrayList;

public class functionASTNode extends callableNode {

    private final String identifier ;
    private final ArrayList<variableNode> parameters ;
    private final ArrayList<variableNode> variables ;
    private final ArrayList<statementNode> statements ;

    public functionASTNode(String identifier, ArrayList<variableNode> parameters, ArrayList<variableNode> variables, ArrayList<statementNode> statements) {
        super(identifier,variables);

        this.identifier = identifier;
        this.parameters = parameters;
        this.variables = variables;
        this.statements = statements ;
    }

    public ArrayList<variableNode> getParameters() {
        return parameters;
    }

    public ArrayList<variableNode> getVariables() {
        return variables;
    }

    public ArrayList<statementNode> getStatements() {
        return statements;
    }

    public String getIdentifier() {
        return identifier;
    }

    // var = not constant, not var = constant.
    @Override
    public String toString() {

        return "FunctionASTnode(" + identifier + ", parameters: (" + parameters + "), variables: (" + variables + "), statements: (" + statements + ")";
    }
}
