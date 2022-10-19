public class floatDataType extends interpreterDataType {

    private float number ;

    public floatDataType(float floatNumber) {
        floatNumber = number ;
    }

    public float getNumber() {
        return number;
    }

    @Override
    public String ToString() {
        return String.valueOf(number);
    }

    @Override
    public void FromString(String input) {
        number = Float.parseFloat(input);
    }
}
