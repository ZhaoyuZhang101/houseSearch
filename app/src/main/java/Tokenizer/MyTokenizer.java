package Tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MyTokenizer {
    ArrayList<String> keywords = new ArrayList<>();
    public String _buffer;
    private Token currentToken;

    public void putKeywords(InputStreamReader reader) {
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";
            while((line = bufferedReader.readLine())!=null) {
                keywords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String containAnyKeyword(String sentence,ArrayList<String> keywords) {
        String result = "";
        for (String i : keywords) {
            if (sentence.contains(i)) {
                result = i;
            }
        }
        return result;
    }

    public boolean containDigit(String input) {
        boolean result = false;
        for (Character i:input.toCharArray()) {
            if (Character.isDigit(i)) {
                result = true;
            }
        }
        return result;
    }


    public MyTokenizer(String text) {
        _buffer = text;		// save input text (string)
        next();		// extracts the first token.
    }

    /**
     *  This function will find and extract a next token from {@code _buffer} and
     *  save the token to {@code currentToken}.
     */
    public void next() {
        if (!_buffer.equals("")) {
            _buffer = _buffer.trim();
            _buffer = _buffer.toLowerCase();
            int i = 1;
            String word = "" + _buffer.charAt(0);
            String keyword = "";
            while (i < _buffer.length()) {
                if (containAnyKeyword(word, keywords).length() == 0) {
                    word = word + _buffer.charAt(i);
                    keyword = containAnyKeyword(word, keywords);
                    i++;
                } else {
                    if (i<_buffer.length()) {
                        if (_buffer.charAt(i) == 's') {
                            keyword = word + 's';
                        } else {
                            keyword = containAnyKeyword(word, keywords);
                        }
                    }
                    break;
                }
            }

            int j = 0;
            StringBuilder number = new StringBuilder();
            while (j < _buffer.length()) {
                if (Character.isDigit(_buffer.charAt(j))) {
                    number.append(_buffer.charAt(j));
                } else if (number.length() != 0 && !Character.isDigit(_buffer.charAt(j))) {
                    break;
                }
                j++;
            }
            String rest = "" + _buffer;


            if (number.length() != 0 && containDigit(word)) {
                currentToken = new Token(number.toString(), Token.Type.INT);
            } else if (keyword.equals("<")
                    | keyword.equals(">")) {
                currentToken = new Token(keyword, Token.Type.COMPARE);
            } else if (keyword.contains("bedroom")
                    | keyword.contains("bathroom")
                    | keyword.contains("garage")) {
                currentToken = new Token(keyword, Token.Type.ROOM);
            } else if (keyword.contains("price")
                    | keyword.equals("~")
                    | keyword.contains("dollar")
                    | keyword.contains("rent")) {
                currentToken = new Token(keyword, Token.Type.PRICE);
            } else if (keyword.contains("apartment")
                    | keyword.contains("unit")
                    | keyword.contains("villa")
                    | keyword.contains("townhouse")
                    | keyword.contains("acreage")
                    | keyword.contains("studio")
                    | keyword.contains("house")) {
                currentToken = new Token(keyword, Token.Type.TYPE);
            } else if (keyword.equals("less")
                    | keyword.equals("greater")) {
                currentToken = new Token(keyword, Token.Type.COMPARE);
            } else {
                currentToken = new Token(keyword, Token.Type.NULL);
            }

            int tokenLen = currentToken.token().length();
            _buffer = _buffer.replaceFirst(currentToken.token(), "");
            rest = rest + _buffer;
        } else {
            currentToken = new Token("", Token.Type.NULL);
        }
    }

    /**
     *  returned the current token extracted by {@code next()}
     *  **** please do not modify this part ****
     *
     * @return type: Token
     */
    public Token current() {
        return currentToken;
    }

    /**
     *  check whether there still exists another tokens in the buffer or not
     *  **** please do not modify this part ****
     *
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken.type() != Token.Type.NULL;
    }
}