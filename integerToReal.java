import java.util.List;

public class integerToReal extends builtInFunctionNode {

    protected integerToReal(boolean isVariadic) {
        super(false);
    }

    @Override
    List<parameterNode> execute(List<interpreterDataType> list) throws Exception {
        List<parameterNode> paramList = null;
        if(list.get(0) instanceof intDataType) {
            paramList.add(new parameterNode(new integerNode(Integer.parseInt(list.get(0).toString())),false));
            paramList.add(new parameterNode(new floatNode(Float.parseFloat(list.get(0).toString())),false));
        } else {throw new Exception("Wrong parameters.");}
        return paramList ;
    }
}
