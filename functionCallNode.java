import java.util.List;

public class functionCallNode extends statementNode {

    private final String identifier ;
    private List<parameterNode> args ;

    public functionCallNode(String identifier,List<parameterNode> args) {

        this.identifier = identifier ;
        this.args = args ;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<parameterNode> getArgs() {
        return args;
    }

    public String toString() {

        return identifier + "(" + args + ")" ;
    }
}
