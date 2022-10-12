import java.util.List;

public class functionCallNode extends statementNode {

    private final String identifier ;
    private List<parameterNode> args ;

    public functionCallNode(String identifier,List<parameterNode> args) {

        this.identifier = identifier ;
        this.args = args ;
    }

    public String toString() {

        return identifier + "(" + args + ")" ;
    }
}
