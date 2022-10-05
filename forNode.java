import java.util.List;

public class forNode extends node {

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

    @Override
    public String toString() {
        return "for(" + var + "," + startNode + "," + endNode + ") {" + statements + "}" ;
    }
}
