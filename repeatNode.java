import java.util.List;

public class repeatNode extends node {

    private booleanExpressionNode bool ;
    private List<statementNode> statements ;

    public repeatNode(booleanExpressionNode bool, List<statementNode> statements) {

        this.bool = bool ;
        this.statements = statements ;

    }

    @Override
    public String toString() {
        return "repeat(" + bool + "){," + statements + "}" ;
    }
}
