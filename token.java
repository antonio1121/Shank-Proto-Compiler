public class token {

    public enum type {
        NUMBER,
        PLUS,
        MINUS,
        TIMES,
        DIVIDE,
        MODULO,
        LPAR,
        RPAR,
        LBR,
        RBR,
        EOL,
        identifier,
        define,
        integer,
        real,
        begin,
        end,
        semicolon,
        colon,
        equal,
        comma,
        variables,
        constants,
        assign,
        iff,
        then,
        elsee,
        elsif,
        forr,
        from,
        to,
        whilee,
        repeat,
        until,
        less,
        greater,
        lequal,
        gequal,
        varr,
        notequal

    }

    private final type type ;
    private String value ;

    public token(type type , String value) {

        this.value = value ;
        this.type = type ;
    }
    public token (type type) {

        this.type = type ;
    }
    // getters are used here to edit tokens once they are created in the lexer method
    public token.type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return type + "(" + value + ")" ;
    }
}
