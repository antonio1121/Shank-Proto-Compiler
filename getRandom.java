import java.util.List;
import java.util.Random;

public class getRandom extends builtInFunctionNode {

    public getRandom() {
        super("getRandom",null,false);
    }

    @Override
    public void execute(List<interpreterDataType> list) {

        Random rand = new Random();
        list.set(0,new intDataType(rand.nextInt()));

    }
}
