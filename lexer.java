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
        reservedWords.put("if", token.type.iff);
        reservedWords.put("then", token.type.then);
        reservedWords.put("else", token.type.elsee);
        reservedWords.put("elsif", token.type.elsif);
        reservedWords.put("for", token.type.forr);
        reservedWords.put("from", token.type.from);
        reservedWords.put("to", token.type.to);
        reservedWords.put("while", token.type.whilee);
        reservedWords.put("repeat", token.type.repeat);
        reservedWords.put("until", token.type.until);
        reservedWords.put("var", token.type.varr);
        reservedWords.put("true", token.type.truee);
        reservedWords.put("false", token.type.falsee);

// marks the end of each line with "\n"
        for (int i = 0; i != charBuff.length ; i++ ) {

            if (charBuff[i] == '0' || charBuff[i] == '1' || charBuff[i] == '2' || charBuff[i] == '3' ||
                    charBuff[i] == '4' || charBuff[i] == '5' || charBuff[i] == '6' || charBuff[i] == '7' ||
                    charBuff[i] == '8' || charBuff[i] == '9' /*|| charbuff[i] == '-' || charbuff[i] == '+' */||
                    charBuff[i] == '.') {

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
                // Looks for the assignment statement by checking for a previous colon, and for boolean comparators.
                try {
                    if (charBuff[i - 1] == ':') {
                        tokenlist.remove(tokenlist.size()-1);
                        tokenlist.add(new token(token.type.assign));

                    } else if(charBuff[i-1] == '>') {
                        tokenlist.remove(tokenlist.size()-1);
                        tokenlist.add(new token(token.type.gequal));

                    } else if (charBuff[i-1] == '<') {
                        tokenlist.remove(tokenlist.size()-1);
                        tokenlist.add(new token(token.type.lequal));
                    }
                    else {
                        tokenlist.add(new token(token.type.equal));
                    }
                } catch(ArrayIndexOutOfBoundsException ignored) {}

            } else if (charBuff[i] == ',') {
                tokenlist.add(new token(token.type.comma));

            } else if (charBuff[i] == '{') {
                tokenlist.add(new token(token.type.LBR));

            } else if (charBuff[i] == '}') {
                tokenlist.add(new token(token.type.RBR));

            } else if (charBuff[i] == '%') {
                tokenlist.add(new token(token.type.MODULO));
// The other boolean comparators are located here.
            } else if (charBuff[i] == '>') {
                try {
                    if(charBuff[i-1] == '<') {
                        tokenlist.remove(tokenlist.size()-1);
                        tokenlist.add(new token(token.type.notequal));

                    } else {
                        tokenlist.add(new token(token.type.greater));
                    }
                } catch(ArrayIndexOutOfBoundsException ignored) {}

            } else if (charBuff[i] == '<') {
                tokenlist.add(new token(token.type.less));
// We add strings and characters here, checking for correct single characters.
            } else if (charBuff[i] == '\'' ) {
                try {
                    if(charBuff[i+2] == '\'' ) {
                        tokenlist.add(new token(token.type.charr,String.valueOf(charBuff[i+1])));
                        i = i + 2;
                    } else {throw new Exception("Illegal character declaration.");}
                } catch(ArrayIndexOutOfBoundsException ignored) {}

            } else if (charBuff[i] == '"') {
                try {
                    String contents = new String(charBuff);
                    int firstContentIndex = contents.indexOf('"');
                    int contentIndex = contents.lastIndexOf('"');
                    if(contentIndex!=-1 && (firstContentIndex!=contentIndex)) {
                        contents = contents.replaceAll("\\s","");
                        contents = contents.substring(1,contents.length()-1);
                        tokenlist.add(new token(token.type.stringg,contents));
                        i = contentIndex ;
                    } else {throw new Exception("Illegal string declaration.");}
                } catch(ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException ignored) {}
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
//todo fix...maybe... so that you don't need spaces in between numbers and operands (probably word buffer(??))
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
                // Checks after outputting all the tokens that the plus are minuses are correctly labeled as such and not numbers,
                // and removes null numbers and identifiers.

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
                    if ((tokenlist.get(i - 2).getType().equals(token.type.PLUS) || tokenlist.get(i - 2).getType().equals(token.type.MINUS) || tokenlist.get(i - 2).getType().equals(token.type.DIVIDE) || tokenlist.get(i - 2).getType().equals(token.type.MODULO) || tokenlist.get(i - 2).getType().equals(token.type.TIMES)) && tokenlist.get(i-3).getType().equals(token.type.NUMBER)) {
                        if (tokenlist.get(i).getType().equals(token.type.NUMBER)) {
                            tokenlist.remove(i - 1);
                        }
                    }
                } if(tokenlist.get(i).getType().equals(token.type.NUMBER) && (tokenlist.get(i).getValue() == null || tokenlist.get(i).getValue().equals(""))) {
                    tokenlist.remove(i);
                } if(tokenlist.get(i).getType().equals(token.type.identifier) && (tokenlist.get(i).getValue().equals(""))) {
                    tokenlist.remove(i);
                }

            } catch(IndexOutOfBoundsException ignored) {}
        } // Again the exception is caught here because we only use it to traverse the elements of the array and not edit them

        // Does one final check to correct negative numbers that are standalone.

        for(int i = 0; i != tokenlist.size(); i++) {

            if(tokenlist.get(i).getType().equals(token.type.NUMBER)) {
                try {
                    if(tokenlist.get(i-1).getType().equals(token.type.MINUS)) {
                        if(!tokenlist.get(i-2).getType().equals(token.type.NUMBER)) {
                             replaceStringBuff = tokenlist.get(i).getValue();
                             replaceStringBuff = "-" + replaceStringBuff ;
                             tokenlist.remove(i);
                             tokenlist.add(i, new token(token.type.NUMBER, replaceStringBuff));
                             tokenlist.remove(i-1);
                        }
                    }
                } catch(IndexOutOfBoundsException ignored) {}
            }
        }

        return tokenlist ;
    }
}
