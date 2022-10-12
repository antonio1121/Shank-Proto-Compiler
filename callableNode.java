import java.util.List;

public abstract class callableNode extends node {

    private String functionName ;
    private List<variableNode> variables ;

    protected callableNode(String functionName, List<variableNode> variables) {

        this.functionName = functionName ;
        this.variables = variables ;
    }

    public String toString() {
        return "callableNode(" + functionName + ") {" + variables + "}" ;
    }
}
