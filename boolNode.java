public class boolNode extends node {

    private final boolean bool ;

    public boolNode(boolean bool) {
        this.bool = bool ;
    }

    @Override
    public String toString() {
        return String.valueOf(bool);
    }
}
