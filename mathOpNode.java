public class mathOpNode extends node {
    enum Op {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, MOD
    }

    private final Op Op ;
    private final node left;
    private final node right ;

    public mathOpNode(node left, node right, Op Op) {

        this.left = left ;
        this.right = right ;
        this.Op = Op ;
    }

    public node getLeft() {
        return left ;
    }
    public node getRight() {
        return right ;
    }
    public Op getOp() {return Op ;}

    @Override
    public String toString() {
        return "mathNode(" + Op + "," + left + "," + right + ")" ;
    }
}
