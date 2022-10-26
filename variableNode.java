public class variableNode extends node {

    private final String identifier;
    private final boolean isConstant;
    private floatNode value;
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
    public variableNode(String identifier, floatNode value, dataType dataType, boolean isConstant) {

        this.identifier = identifier;
        this.value = value;
        this.dataType = dataType ;
        this.isConstant = isConstant ;
    }

    public String getIdentifier() {
        return identifier;
    }

    public floatNode getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "VariableNode( " + identifier + " = " + value + " ,isConstant: " + isConstant + ")" ;
    }
}
