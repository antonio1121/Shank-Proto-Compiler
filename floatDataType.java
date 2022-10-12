public class floatDataType extends interpreterDataType {

private float number ;

    @Override
    public String ToString() {
        return String.valueOf(number);
    }

    @Override
    public void FromString(String input) {
        number = Float.parseFloat(input);
    }
}
