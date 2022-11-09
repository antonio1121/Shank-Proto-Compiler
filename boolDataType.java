public class boolDataType extends interpreterDataType {

    private boolean bool ;


    public boolDataType(boolean bool) {
        this.bool = bool ;
    }

    @Override
    public String ToString() {
        return String.valueOf(bool);
    }

    @Override
    public void FromString(String input) {
        bool = input.equalsIgnoreCase("true");
    }
}
