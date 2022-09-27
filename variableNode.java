public class variableNode extends node {

    private String identifier;
    private boolean isConstant;
    private float floater;
    private dataType dataType;

    public enum dataType {
        INTEGER, REAL
    }
// different constructors used for the variableNode depending on if the variable is assigned with data or not.
    public variableNode(String identifier,dataType dataType, boolean isConstant) {
        this.identifier = identifier;
        this.dataType = dataType ;
        this.isConstant = isConstant ;
    }
    public variableNode(String identifier, float floater, dataType dataType, boolean isConstant) {

        this.identifier = identifier;
        this.floater = floater ;
        this.dataType = dataType ;
        this.isConstant = isConstant ;
    }

    @Override
    public String toString() {
        return "VariableNode( " + identifier + " = " + floater + " ,isConstant: " + isConstant + ")" ;
    }
}
