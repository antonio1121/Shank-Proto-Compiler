public class booleanExpressionNode extends node {

    enum compare {

        greater,
        lessThan,
        gEquals,
        lEquals,
        Equals,
        notEqual

    }

    private final compare compare ;
    private final node leftExpression ;
    private final node rightExpression ;

    public booleanExpressionNode(node leftExpression, node rightExpression, compare compare) {

        this.leftExpression = leftExpression ;
        this.rightExpression = rightExpression ;
        this.compare = compare ;

    }

    public node getLeftExpression() {
        return leftExpression;
    }

    public node getRightExpression() {
        return rightExpression;
    }

    public booleanExpressionNode.compare getCompare() {
        return compare;
    }

    @Override
    public String toString() {
        return "booleanExpression(" + compare + "," + leftExpression + "," + rightExpression + ")" ;
    }
}
