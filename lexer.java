import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.regex.Pattern;



@SuppressWarnings("StringConcatenationInLoop")
public class lexer {
    // list to store all the tokens in
    List<token> tokenlist = new ArrayList<>() ;
    // buffers used to retain our tokens before being outputted
    char[] charBuff;
    String sbuff = "" ;
    String wordBuff = "" ;
    String replaceStringBuff ;
    // test value for illegal decimal
    int illegalDecimal = 0 ;
    // HashMap used to store the different types of reserved words
// define, integer, real, begin, end, variables, constants NOT semicolon colon equal comma
    static HashMap<String,token.type> reservedWords = new HashMap<>();

    public List<token> lex(String line) throws Exception {
// converts input lines into char array for analysis
        charBuff = line.toCharArray();
        charBuff = Arrays.copyOf(charBuff, charBuff.length + 1) ;
        charBuff[charBuff.length-1] = '\n' ;
// Data initialized for hashMap
        reservedWords.put("define", token.type.define);
        reservedWords.put("integer", token.type.integer);
        reservedWords.put("real", token.type.real);
        reservedWords.put("begin", token.type.begin);
        reservedWords.put("end", token.type.end);
        reservedWords.put("variables", token.type.variables);
        reservedWords.put("constants", token.type.constants);

// marks the end of each line with "\n"
        for (int i = 0; i != charBuff.length ; i++ ) {

            if (charBuff[i] == '0' || charBuff[i] == '1' || charBuff[i] == '2' || charBuff[i] == '3' || charBuff[i] == '4' || charBuff[i] == '5' || charBuff[i] == '6' || charBuff[i] == '7' || charBuff[i] == '8' || charBuff[i] == '9' /*|| charbuff[i] == '-' || charbuff[i] == '+' */|| charBuff[i] == '.') {

                if (illegalDecimal == 2) {
                    System.out.println(tokenlist);
                    throw new Exception("Illegal Line. More than one '.' in a token");
                }

                if (charBuff[i] == '.') {
                    illegalDecimal++ ;
                }
// Counters count the amount of illegal character build up in a token
                sbuff += charBuff[i];
// Resets counter upon a white space
                try {
                    if (charBuff[i + 1] == ' ') {
                        sbuff = sbuff.replaceAll("\\s", "");
                        tokenlist.add(new token(token.type.NUMBER, sbuff));
                        sbuff = "";

                    }
                } catch(ArrayIndexOutOfBoundsException ignored) {}
// Exception is caught here because we do not edit the array out of bounds ; instead we just want to check the next character if it exists or not.
            } else if (charBuff[i] == '+') {
                tokenlist.add(new token(token.type.PLUS));

            } else if (charBuff[i] == '-') {

                tokenlist.add(new token(token.type.MINUS));

            } else if (charBuff[i] == '*') {
                tokenlist.add(new token(token.type.TIMES));

            } else if (charBuff[i] == '/') {
                tokenlist.add(new token(token.type.DIVIDE));

            } else if (charBuff[i] == '(') {
                // Looks for the comment tag (parenthesis and asterisk) to be ignored by the lexer.
                try {
                    int commentIndex ;
                    if(charBuff[i+1] == '*') {
                        String comment = new String(charBuff);
                        commentIndex = comment.indexOf(')');
                            if(comment.charAt(commentIndex - 1) == '*') {
                                i = commentIndex ;
                            }

                    } else {tokenlist.add(new token(token.type.LPAR)); }

                } catch(ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException ignored) {}

            } else if (charBuff[i] == ')') {
                tokenlist.add(new token(token.type.RPAR));

            } else if (charBuff[i] == ';') {
                tokenlist.add(new token(token.type.semicolon));

            } else if (charBuff[i] == ':') {
                tokenlist.add(new token(token.type.colon));

            } else if (charBuff[i] == '=') {
                // Looks for the assignment statement by checking for a previous colon.
                try {
                    if (charBuff[i - 1] == ':') {
                        tokenlist.remove(tokenlist.size()-1);
                        tokenlist.add(new token(token.type.assign));
                    } else {
                        tokenlist.add(new token(token.type.equal));
                    }
                } catch(ArrayIndexOutOfBoundsException ignored) {}

            } else if (charBuff[i] == ',') {
                tokenlist.add(new token(token.type.comma));

            } else if (charBuff[i] == '{') {
                tokenlist.add(new token(token.type.LBR));

            } else if (charBuff[i] == '}') {
                tokenlist.add(new token(token.type.RBR));

            }
// All the operators are added here
            else if (charBuff[i] == ' ') {

                sbuff += charBuff[i];
                illegalDecimal = 0 ;

            } else if ( i == charBuff.length - 1) {

                if (!sbuff.isEmpty()) {
                    sbuff = sbuff.replaceAll("\\s", "");
                    tokenlist.add(new token(token.type.NUMBER, sbuff));
                }
                tokenlist.add(new token(token.type.EOL));
                sbuff = "" ;
                sbuff = sbuff.replaceAll("\\s", "");


            } else {
                wordBuff += charBuff[i] ;

                try {
                    String futureWordBuff = String.valueOf(charBuff[i+1]);
                    // Looks in the hashmap if the word buffer has something equal to the reserved words in shank. If it does, it adds the token.
                    if (charBuff[i + 1] == ' ' || !(Pattern.matches("[a-zA-Z]",futureWordBuff))) {
                        if(reservedWords.containsKey(wordBuff)) {
                            tokenlist.add(new token(reservedWords.get(wordBuff)));
                        } else {
                            wordBuff = wordBuff.replaceAll("\\s", "");
                            tokenlist.add(new token(token.type.identifier, wordBuff));
                        }
                        wordBuff = "";
                    }
                } catch(ArrayIndexOutOfBoundsException ignored) {} // The exception is caught here because we only use it to traverse the elements of the array and not edit them
            }
        } for (int i = 0 ; i != tokenlist.size(); i++) {
            try {
                // Checks after outputting all the tokens that the plus are minuses are correctly labeled as such and not numbers, and removes null numbers

                if(tokenlist.get(i).getType().equals((token.type.MINUS))) {
                    if(tokenlist.get(i-1).getType().equals((token.type.MINUS))) {
                        if (tokenlist.get(i - 2).getType().equals((token.type.MINUS))) {
                            throw new Exception("Illegal Line. Too many minuses.");
                        }
                    }
                }
                if(tokenlist.get(i).getType().equals((token.type.PLUS))) {
                    if(tokenlist.get(i-1).getType().equals((token.type.PLUS))) {
                        if (tokenlist.get(i - 2).getType().equals((token.type.PLUS))) {
                            throw new Exception("Illegal Line. Too many pluses.");
                        }
                    }
                }
                if(tokenlist.get(i-1).getType().equals((token.type.MINUS))) {
                    if(tokenlist.get(i-2).getType().equals((token.type.MINUS))) {
                        if(tokenlist.get(i).getType().equals(token.type.NUMBER)) {
                            replaceStringBuff = tokenlist.get(i).getValue() ;
                            replaceStringBuff = "-" + replaceStringBuff ;
                            tokenlist.remove(i);
                            tokenlist.add(i, new token(token.type.NUMBER, replaceStringBuff));
                            tokenlist.remove(i-1);

                        }
                    }
                } if(tokenlist.get(i-1).getType().equals((token.type.PLUS))) {
                    if (tokenlist.get(i - 2).getType().equals((token.type.PLUS)) && tokenlist.get(i-3).getType().equals(token.type.NUMBER)) {
                        if (tokenlist.get(i).getType().equals(token.type.NUMBER)) {
                            tokenlist.remove(i - 1);
                        }
                    }
                } if(tokenlist.get(i).getType().equals(token.type.NUMBER) && tokenlist.get(i).getValue().equals("")) {
                    tokenlist.remove(i);
                }

            } catch(IndexOutOfBoundsException ignored) {}
        } // Again the exception is caught here because we only use it to traverse the elements of the array and not edit them

        return tokenlist ;
    }
}
