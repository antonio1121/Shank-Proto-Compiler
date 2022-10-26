import java.util.List;

public abstract class builtInFunctionNode extends callableNode {
// we make a hashmap w all default functions in main, that's where BIF are known and stored so that they can be called in interpreter
    private boolean isVariadic ;


    protected builtInFunctionNode(String functionName, List<variableNode> variables, boolean isVariadic) {
        super(functionName,variables);
        this.isVariadic = isVariadic;

    }



    public abstract void execute(List<interpreterDataType> list) throws Exception;
}
