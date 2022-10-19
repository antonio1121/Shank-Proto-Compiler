import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class interpreter {

    static HashMap<String, interpreterDataType> variableList = new HashMap<>();


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
        float leftNode;
        float rightNode;
        if (node.getLeft() instanceof mathOpNode) {
            simplify((mathOpNode) node.getLeft());

        }
        if (node.getRight() instanceof mathOpNode) {
            simplify((mathOpNode) node.getRight());

        }
        if (node.getOp().equals(mathOpNode.Op.ADD)) {
            return Float.parseFloat(node.getLeft().toString()) + Float.parseFloat(node.getRight().toString());

        } else if (node.getOp().equals(mathOpNode.Op.SUBTRACT)) {
            return Float.parseFloat(node.getLeft().toString()) - Float.parseFloat(node.getRight().toString());

        } else if (node.getOp().equals(mathOpNode.Op.MULTIPLY)) {
            return Float.parseFloat(node.getLeft().toString()) * Float.parseFloat(node.getRight().toString());

        } else if (node.getOp().equals(mathOpNode.Op.DIVIDE)) {
            return Float.parseFloat(node.getLeft().toString()) / Float.parseFloat(node.getRight().toString());

        } else if (node.getOp().equals(mathOpNode.Op.MOD)) {
            return Float.parseFloat(node.getLeft().toString()) % Float.parseFloat(node.getRight().toString());
        } else return 0;
    }

    public static void interpretFunction(functionASTNode function, List<interpreterDataType> parameters) throws Exception {
        int i = 0;

        for (interpreterDataType parameter : parameters) {
            variableList.put(function.getParameters().get(i).toString(), parameter);
            i++;
        }

        for (variableNode variable : function.getVariables()) {
            variableList.put(variable.toString(), new floatDataType(variable.getFloater().getFloatNumber()));
        }

        interpretBlock(function.getStatements());
    }

    public static void interpretBlock(List<statementNode> statements) throws Exception {
        for (statementNode statement : statements) {
            if (statement instanceof functionCallNode) {
                if(shank.functionList.containsKey(((functionCallNode) statement).getIdentifier())) {
                    List<interpreterDataType> values = new ArrayList<>();
                    for(parameterNode parameter : ((functionCallNode) statement).getArgs()) {
                        // How the hell do we do this?????????
                        // values = new floatDataType(Float.parseFloat(parameter.toString())) ;
                    }

                } else {throw new Exception("Function does not exist.");}
            }
        }

    }
}

