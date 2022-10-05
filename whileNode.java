import java.util.List;

public class whileNode extends node {

    private booleanExpressionNode bool ;
    private List<statementNode> statements ;

    public whileNode(booleanExpressionNode bool, List<statementNode> statements) {

        this.bool = bool ;
        this.statements = statements ;

    }

    @Override
    public String toString() {
        return "while(" + bool + ") {" + statements + "}" ;
    }
}
