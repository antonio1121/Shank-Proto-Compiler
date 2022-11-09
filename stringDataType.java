public class stringDataType extends interpreterDataType {

    private String content ;

    public stringDataType(String content) {
        this.content = content ;
    }
    @Override
    public String ToString() {
        return content;
    }

    @Override
    public void FromString(String input) {
            content = input ;
    }
}
