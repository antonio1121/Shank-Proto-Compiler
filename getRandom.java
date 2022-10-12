import java.util.List;
import java.util.Random;

public class getRandom extends builtInFunctionNode {


    protected getRandom(boolean isVariadic) {
        super(isVariadic);
    }

    @Override
    List<parameterNode> execute(List<interpreterDataType> list) {

        List<parameterNode> paramList = null ;
        Random rand = new Random();
        paramList.add(new parameterNode(new integerNode(rand.nextInt()),true));
        return paramList ;

    }
}
