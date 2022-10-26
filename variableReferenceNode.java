public class variableReferenceNode extends node {

    private final String name ;
    private final boolean isInt ;

    public variableReferenceNode(String name,boolean isInt) {
        this.name = name ;
        this.isInt = isInt;
    }

    @Override
    public String toString() {
        return name + ", is int: " + isInt;
    }

    public String getName() {
        return name;
    }

    public boolean isInt() {
        return isInt;
    }
}
