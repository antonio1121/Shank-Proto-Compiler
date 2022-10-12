import java.io.IOException;

public class interpreter {

    public interpreter() {

    }

    public float resolve(node node) throws IOException {

        if (node instanceof floatNode) {
            return ((floatNode) node).getFloatNumber();
        } else if (node instanceof integerNode) {
            return (((integerNode) node).getIntNumber());
        } else if (node instanceof mathOpNode) {
            return simplify((mathOpNode) node);
        } else {
            throw new IOException("Cannot resolve correctly.");
        }
    }

    public float simplify(mathOpNode node) {
        float leftNode ;
        float rightNode ;
        if(node.getLeft() instanceof mathOpNode) {
            simplify((mathOpNode) node.getLeft());

        } if(node.getRight() instanceof mathOpNode) {
            simplify((mathOpNode) node.getRight());

        }
        if (node.getOp().equals(mathOpNode.Op.ADD)) {
            return Float.parseFloat(node.getLeft().toString()) + Float.parseFloat(node.getRight().toString());

        } else if(node.getOp().equals(mathOpNode.Op.SUBTRACT)) {
            return Float.parseFloat(node.getLeft().toString()) - Float.parseFloat(node.getRight().toString());

        } else if(node.getOp().equals(mathOpNode.Op.MULTIPLY)) {
            return Float.parseFloat(node.getLeft().toString()) * Float.parseFloat(node.getRight().toString());

        } else if(node.getOp().equals(mathOpNode.Op.DIVIDE)) {
            return Float.parseFloat(node.getLeft().toString()) / Float.parseFloat(node.getRight().toString());

        } else if(node.getOp().equals(mathOpNode.Op.MOD)) {
            return Float.parseFloat(node.getLeft().toString()) % Float.parseFloat(node.getRight().toString());
        } else return 0 ;
    }
}

