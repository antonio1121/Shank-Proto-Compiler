import java.util.List;
import java.util.Scanner;

public class read extends builtInFunctionNode {

    public read() {
        super("read",null,true);
    }

    @Override
    public void execute(List<interpreterDataType> list) {

        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()) {
            list.add(new floatDataType(scan.nextFloat()));
        }
    }
}
