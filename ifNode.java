import java.util.List;

public class ifNode extends node {

    private final booleanExpressionNode bool ;
    private final List<statementNode> statementList ;
    private final ifNode ifNested ;
    private final elseNode elsee ;

    public ifNode(booleanExpressionNode bool, List<statementNode> statementList, ifNode ifNested,elseNode elsee) {

        this.bool = bool ;
        this.statementList = statementList ;
        this.ifNested = ifNested ;
        this.elsee = elsee ;
    }

    @Override
    public String toString() {
        return "If(" + bool + ") {" + statementList + "}" + ifNested ;
    }
}
