public class intDataType extends interpreterDataType {

    private int number ;

    @Override
    public String ToString() {
        return String.valueOf(number);
    }

    @Override
    public void FromString(String input) {
        number = Integer.parseInt(input);
    }
}
