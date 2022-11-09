public class stringNode extends node {

    private final String value ;

    public stringNode(String value) {
        this.value = value ;
    }

    @Override
    public String toString() {
        return value;
    }
}
