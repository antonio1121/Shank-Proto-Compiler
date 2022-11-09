public class charNode extends node {

    private final char character ;

    public charNode(char character) {
        this.character = character ;
    }
     public String toString() {
        return String.valueOf(character);
     }
}
