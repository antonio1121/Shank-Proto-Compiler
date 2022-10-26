import java.util.ArrayList;
import java.util.List;

public class integerToReal extends builtInFunctionNode {
    private static final ArrayList<variableNode> p = new ArrayList<>();
    public integerToReal() {
        super("integerToReal",p,false);
        p.add(new variableNode("someInteger", variableNode.dataType.REAL,true));
        p.add(new variableNode("someReal", variableNode.dataType.INTEGER,false));
    }

    @Override
    public void execute(List<interpreterDataType> list) throws Exception {
        if(list.get(0) instanceof intDataType) {
            list.set(1,new floatDataType((float)(((intDataType) list.get(0)).getNumber())));
        } else {throw new Exception("Wrong parameters.");}
    }
}
