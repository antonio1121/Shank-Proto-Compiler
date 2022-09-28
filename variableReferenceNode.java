public class variableReferenceNode extends node {

    private final String name ;

    public variableReferenceNode(String name) {
        this.name = name ;
    }

    @Override
    public String toString() {
        return name;
    }
}
