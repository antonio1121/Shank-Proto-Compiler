public class assignmentNode extends statementNode {

    private final variableReferenceNode vrn ;
    private final mathOpNode expression ;

    public assignmentNode(variableReferenceNode vrn, mathOpNode expression) {

        this.vrn = vrn ;
        this.expression = expression ;
    }

    @Override
    public String toString() {

        return "variable " + vrn + " = " + expression ;
    }
}
