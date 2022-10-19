public class intDataType extends interpreterDataType {

    private int number ;

    public intDataType(int number) {
        this.number = number ;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String ToString() {
        return String.valueOf(number);
    }

    @Override
    public void FromString(String input) {
        number = Integer.parseInt(input);
    }
}
