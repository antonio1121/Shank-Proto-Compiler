import java.util.List;

public class write extends builtInFunctionNode {

    public write() {
        super("write",null,true);
    }

    @Override
    public void execute(List<interpreterDataType> list) {
      for(interpreterDataType elements: list) {
          System.out.println(elements);
      }
    }
}
