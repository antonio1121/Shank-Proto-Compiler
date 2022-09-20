import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class lexer {
// list to store all the tokens in
    List<token> tokenlist = new ArrayList<>() ;
// buffers used to retain our tokens before being outputted
    char[] charbuff ;
    String sbuff = "" ;
    // StringBuilder replaceStringBuff = new StringBuilder() ;
    String replaceStringBuff ;
// test value for illegal decimal
    int illegalDecimal = 0 ;
    public List<token> lex(String line) throws Exception {
// converts input lines into char array for analysis
        charbuff = line.toCharArray();
        charbuff = Arrays.copyOf(charbuff, charbuff.length + 1) ;
        charbuff[charbuff.length-1] = 'E' ;
// marks the end of each line with "E"
        for (int i = 0 ; i != charbuff.length ; i++ ) {

            if (charbuff[i] == '0' || charbuff[i] == '1' || charbuff[i] == '2' || charbuff[i] == '3' || charbuff[i] == '4' || charbuff[i] == '5' || charbuff[i] == '6' || charbuff[i] == '7' || charbuff[i] == '8' || charbuff[i] == '9' /*|| charbuff[i] == '-' || charbuff[i] == '+' */|| charbuff[i] == '.') {

                if (illegalDecimal == 2) {
                    System.out.println(tokenlist);
                    throw new Exception("Illegal Line. More than one '.' in a token");
                }

                if (charbuff[i] == '.') {
                    illegalDecimal++ ;
                }
// Counters count the amount of illegal character build up in a token
                sbuff += charbuff[i];
// Resets counter upon a white space
                try {
                    if (charbuff[i + 1] == ' ') {
                        sbuff = sbuff.replaceAll("\\s", "");
                        tokenlist.add(new token(token.type.NUMBER, sbuff));
                        sbuff = "";

                    }
                } catch(ArrayIndexOutOfBoundsException ignored) {}
// Exception is caught here because we do not edit the array out of bounds ; instead we just want to check the next character if it exists or not.
            } else if (charbuff[i] == '+') {
                tokenlist.add(new token(token.type.PLUS));

            } else if (charbuff[i] == '-') {

                tokenlist.add(new token(token.type.MINUS));

            } else if (charbuff[i] == '*') {
                tokenlist.add(new token(token.type.TIMES));

            } else if (charbuff[i] == '/') {
                tokenlist.add(new token(token.type.DIVIDE));

            } else if (charbuff[i] == '(') {
                tokenlist.add(new token(token.type.LPAR));

            } else if (charbuff[i] == ')') {
                tokenlist.add(new token(token.type.RPAR));

            }
// All the operators are added here
            else if (charbuff[i] == ' ') {

                sbuff += charbuff[i];
                illegalDecimal = 0 ;

            } else if ( i == charbuff.length - 1) {

                if (sbuff.isEmpty()==false) {
                    sbuff = sbuff.replaceAll("\\s", "");
                    tokenlist.add(new token(token.type.NUMBER, sbuff));
                }
                tokenlist.add(new token(token.type.EOL));
                sbuff = "" ;
                sbuff = sbuff.replaceAll("\\s", "");


            }
        } for (int i = 0 ; i != tokenlist.size(); i++) {
            try {
                // Checks after outputting all the tokens that the plus are minuses are correctly labeled as such and not numbers, and removes null numbers
               // System.out.println(tokenlist.get(i));

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
                } if(tokenlist.get(i).getType().equals(token.type.NUMBER) && tokenlist.get(i).getValue() == "") {
                    tokenlist.remove(i);
                }

            } catch(IndexOutOfBoundsException ignored) {}
        } // Again the exception is caught here because we only use it to traverse the elements of the array and not edit them

        return tokenlist ;
    }
}
