public class charDataType extends interpreterDataType {

    private char character ;

    public charDataType(char character) {
        this.character = character ;
    }
    @Override
    public String ToString() {
        return String.valueOf(character);
    }

    @Override
    public void FromString(String input) {
        character = input.charAt(0) ;
    }

    public char getCharacter() {
        return character;
    }
}
