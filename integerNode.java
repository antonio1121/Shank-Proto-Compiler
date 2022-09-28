public class integerNode extends node {

    private final int intNumber ;

    public integerNode(int intNumber) {
        this.intNumber = intNumber;
    }

    public int getIntNumber() {

        return intNumber;
    }

    @Override
    public String toString() {
        return Integer.toString(intNumber);
    }
}
