import java.util.List;

public class ifNode extends statementNode {

    private final booleanExpressionNode bool ;
    private final List<statementNode> statementList ;
    private final List<ifNode> ifNested ;
    private final elseNode elsee ;

    public ifNode(booleanExpressionNode bool, List<statementNode> statementList, List<ifNode> ifNested,elseNode elsee) {

        this.bool = bool ;
        this.statementList = statementList ;
        this.ifNested = ifNested ;
        this.elsee = elsee ;
    }

    public booleanExpressionNode getBool() {
        return bool;
    }

    public List<statementNode> getStatementList() {
        return statementList;
    }

    public List<ifNode> getIfNested() {
        return ifNested;
    }

    public elseNode getElsee() {
        return elsee;
    }

    @Override
    public String toString() {
        return "If(" + bool + ") {" + statementList + "}" + ifNested ;
    }
}
