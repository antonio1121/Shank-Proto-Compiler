public class variableNode extends node {

    private final String identifier;
    private final boolean isConstant;
    private floatNode floater;
    private final dataType dataType;

    public enum dataType {
        INTEGER, REAL
    }
// different constructors used for the variableNode depending on if the variable is assigned with data or not.
    public variableNode(String identifier,dataType dataType, boolean isConstant) {
        this.identifier = identifier;
        this.dataType = dataType ;
        this.isConstant = isConstant ;
    }
    public variableNode(String identifier, floatNode floater, dataType dataType, boolean isConstant) {

        this.identifier = identifier;
        this.floater = floater ;
        this.dataType = dataType ;
        this.isConstant = isConstant ;
    }

    public floatNode getFloater() {
        return floater;
    }

    @Override
    public String toString() {
        return "VariableNode( " + identifier + " = " + floater + " ,isConstant: " + isConstant + ")" ;
    }
}
