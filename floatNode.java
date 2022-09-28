public class floatNode extends node {

    private final float floatNumber ;

    public floatNode(float floatNumber) {
        this.floatNumber = floatNumber;
    }

    public float getFloatNumber() {

        return floatNumber;
    }

    @Override
    public String toString() {
        return Float.toString(floatNumber);
    }
}
