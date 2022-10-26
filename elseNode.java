import java.util.List;

public class elseNode extends node {
// how to extend to if node? causes so many problems with inheritance and default constructors
    private final List<statementNode> statementList ;

    public elseNode(List<statementNode> statementList) {
        this.statementList = statementList ;
    }

    public List<statementNode> getStatementList() {
        return statementList;
    }

    @Override
    public String toString() {
        return "else{" + statementList + "}" ;
    }
}
