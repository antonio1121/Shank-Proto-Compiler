import java.util.ArrayList;
import java.util.List;

public class realToInteger extends builtInFunctionNode {
    private static ArrayList<variableNode> p = new ArrayList<>() ;
    private static realToInteger instance = null;
    public static realToInteger getInstance() {
        if (instance==null)
            instance = new realToInteger();
        return instance;
    }
    private realToInteger() {
        super("realToInteger",p,false);
        p.add(new variableNode("someReal", variableNode.dataType.REAL,true));
        p.add(new variableNode("someInteger", variableNode.dataType.INTEGER,false));
    }

    @Override
    public void execute(List<interpreterDataType> list) throws Exception {
        if(list.get(0) instanceof floatDataType) {
           list.set(1,new floatDataType(((floatDataType) list.get(0)).getNumber()));
        } else {throw new Exception("Wrong parameters.");}
    }
}
