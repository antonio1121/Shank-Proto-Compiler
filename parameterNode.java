public class parameterNode extends node {

    private node parameter ;
    private boolean isVar ;

    public parameterNode(node parameter,boolean isVar) {

        this.parameter = parameter ;
        this.isVar = isVar ;
    }

    public node getParameter() {
        return parameter;
    }

    public boolean getIsVar() {
        return isVar;
    }

    @Override
    public String toString() {
            return "Parameter(" + parameter + "," + isVar + ")" ;
    }
}
