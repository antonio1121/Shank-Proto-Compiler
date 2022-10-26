import java.util.List;

public class forNode extends statementNode {

    private variableReferenceNode var ;
    private node startNode ;
    private node endNode ;
    private List<statementNode> statements ;

    public forNode(variableReferenceNode var, node startNode, node endNode, List<statementNode> statements) {

        this.var = var ;
        this.startNode = startNode ;
        this.endNode = endNode ;
        this.statements = statements ;

    }

    public List<statementNode> getStatements() {
        return statements;
    }

    public node getEndNode() {
        return endNode;
    }

    public node getStartNode() {
        return startNode;
    }

    public variableReferenceNode getVar() {
        return var;
    }

    @Override
    public String toString() {
        return "for(" + var + "," + startNode + "," + endNode + ") {" + statements + "}" ;
    }
}
