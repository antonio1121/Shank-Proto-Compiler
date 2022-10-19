import java.util.List;

public class realToInteger extends builtInFunctionNode {

    public realToInteger() {
        super("realToInteger",null,false);
    }
// todo is this correct constructor call?
    @Override
    public void execute(List<interpreterDataType> list) throws Exception {
        if(list.get(0) instanceof floatDataType) {
           list.set(1,new floatDataType(((floatDataType) list.get(0)).getNumber()));
        } else {throw new Exception("Wrong parameters.");}
    }
}
