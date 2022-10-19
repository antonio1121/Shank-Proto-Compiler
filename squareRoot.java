import java.util.List;

public class squareRoot extends builtInFunctionNode {

    public squareRoot() {
        super("squareRoot",null,false);
    }

    @Override
    public void execute(List<interpreterDataType> list) throws Exception {
        if(list.get(0) instanceof floatDataType && list.get(1) instanceof floatDataType) {
            list.set(1, new floatDataType((float) Math.sqrt(((floatDataType) list.get(0)).getNumber())));
        } else {
            throw new Exception("Parameters are not declared floats.");
        }

    }
}
