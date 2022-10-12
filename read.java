import java.util.List;
import java.util.Scanner;

public class read extends builtInFunctionNode {

    protected read(boolean isVariadic) {
        super(true);
    }

    @Override
    List<parameterNode> execute(List<interpreterDataType> list) {

        List<parameterNode> paramList = null ;
        Scanner scan = new Scanner(System.in);
    // todo finish this
        return paramList ;

    }
}
