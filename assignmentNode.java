public class assignmentNode extends statementNode {

    private final variableReferenceNode vrn ;
    private final node expression ;

    public assignmentNode(variableReferenceNode vrn, node expression) {

        this.vrn = vrn ;
        this.expression = expression ;
    }

    @Override
    public String toString() {

        return "variable " + vrn + " = " + expression ;
    }
}
