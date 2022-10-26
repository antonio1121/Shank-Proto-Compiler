import java.util.List;

public class whileNode extends statementNode {

    private booleanExpressionNode bool ;
    private List<statementNode> statements ;

    public whileNode(booleanExpressionNode bool, List<statementNode> statements) {

        this.bool = bool ;
        this.statements = statements ;

    }

    public booleanExpressionNode getBool() {
        return bool;
    }

    public List<statementNode> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        return "while(" + bool + ") {" + statements + "}" ;
    }
}
