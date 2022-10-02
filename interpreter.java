import java.io.IOException;

public class interpreter {

    public float resolve(node node) throws IOException {

        if (node instanceof floatNode) {
            return ((floatNode) node).getFloatNumber();
        } else if (node instanceof integerNode) {
            return (((integerNode) node).getIntNumber());
        } else if (node instanceof mathOpNode) {
            int a, b,c ;


        } else {throw new IOException("Cannot resolve correctly.");}
        return 0 ;
    }

    public float simplify(float left, float right, mathOpNode.Op Op) {
        //  if(math)
        return 0 ;
    }
}

