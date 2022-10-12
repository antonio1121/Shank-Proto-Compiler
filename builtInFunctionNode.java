import java.util.List;

public abstract class builtInFunctionNode {

    private boolean isVariadic ;

    protected builtInFunctionNode(boolean isVariadic) {

        this.isVariadic = isVariadic;
    }

    abstract List<parameterNode> execute(List<interpreterDataType> list) throws Exception;
}
