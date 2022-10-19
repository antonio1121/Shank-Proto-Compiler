public class parameterNode extends node {

    private node parameter ;
    private String identifier ;
    private boolean isVar ;

    public parameterNode(node parameter,boolean isVar) {
// todo is parameter supposed to be of type node?
        this.parameter = parameter ;
        this.isVar = isVar ;
    }
    public parameterNode(String identifier,boolean isVar) {

        this.identifier = identifier ;
        this.isVar = isVar ;
    }

    @Override
    public String toString() {

        if (parameter!=null) {
            return "Parameter(" + parameter + "," + isVar + ")" ;
        } else if (identifier!=null) {
            return "Parameter(" + identifier + "," + isVar + ")" ;
        } else {
            return "Parameter not created correctly." ;
        }
    }
}
