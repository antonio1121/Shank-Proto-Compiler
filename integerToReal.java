import java.util.List;

public class integerToReal extends builtInFunctionNode {

    public integerToReal() {
        super("integerToReal",null,false);
    }

    @Override
    public void execute(List<interpreterDataType> list) throws Exception {
        if(list.get(0) instanceof intDataType) {
            list.set(1,new floatDataType((float)(((intDataType) list.get(0)).getNumber())));
        } else {throw new Exception("Wrong parameters.");}
    }
}
