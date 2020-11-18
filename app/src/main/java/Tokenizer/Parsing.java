package Tokenizer;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class Parsing {

    static ArrayList<ArrayList<Token>> tokens = new ArrayList<>();

    public static ArrayList<ArrayList<Token>> getTokens(String input, InputStreamReader reader) {
        tokens.clear();
        extractKeywords(input,reader);
        return tokens;
    }

    public static void extractKeywords(String input, InputStreamReader reader) {
        tokens.clear();
        MyTokenizer myTokenizer = new MyTokenizer(input);
        myTokenizer.putKeywords(reader);
        for (int i=0;i<4;i++) {
            tokens.add(new ArrayList<Token>());
        }
        while (myTokenizer.hasNext()) {
            Token token = myTokenizer.current();
            if (token.type() == Token.Type.PRICE) {
                if (myTokenizer.hasNext()) {
                    myTokenizer.next();
                    Token tokenNext = myTokenizer.current();
                    if (tokenNext.type() == Token.Type.INT) {
                        Token putin = new Token("=|"+tokenNext.token(), Token.Type.PRICE);
                        tokens.get(0).add(putin);
                    } else if (tokenNext.type() == Token.Type.COMPARE) {
                        if (myTokenizer.hasNext()) {
                            myTokenizer.next();
                            Token tokenNextNext = myTokenizer.current();
                            if (tokenNextNext.type()== Token.Type.INT) {
                                Token putin = new Token(tokenNext.token()+"|"+tokenNextNext.token(), Token.Type.PRICE);
                                tokens.get(0).add(putin);
                            }
                        }
                    }
                }
            } else if (token.type() == Token.Type.ROOM) {
                if (myTokenizer.hasNext()) {
                    myTokenizer.next();
                    Token tokenNext = myTokenizer.current();
                    if (tokenNext.type() == Token.Type.INT) {
                        Token putin = new Token("=|"+tokenNext.token()+"|"+token.token(), Token.Type.ROOM);
                        tokens.get(2).add(putin);
                    } else if (tokenNext.type() == Token.Type.COMPARE) {
                        if (myTokenizer.hasNext()) {
                            myTokenizer.next();
                            Token tokenNextNext = myTokenizer.current();
                            if (tokenNextNext.type()== Token.Type.INT) {
                                Token putin = new Token(tokenNext.token() + "|" + tokenNextNext.token()+"|"+token.token(), Token.Type.ROOM);
                                tokens.get(2).add(putin);
                            }
                        }
                    }
                }
            } else if (token.type() == Token.Type.TYPE) {
                tokens.get(1).add(token);
            } else if (token.type() == Token.Type.INT) {
                if (myTokenizer.hasNext()) {
                    myTokenizer.next();
                    Token tokenNext = myTokenizer.current();
                    if (tokenNext.type() == Token.Type.ROOM) {
                        Token putin = new Token("=|"+token.token()+"|"+tokenNext.token(),Token.Type.ROOM);
                        tokens.get(2).add(putin);
                    } else if (tokenNext.type() == Token.Type.PRICE) {
                        Token putin = new Token("=|"+token.token(),Token.Type.PRICE);
                        tokens.get(0).add(putin);
                    }
                } else {
                    Token putin = new Token("=|"+token.token(),Token.Type.PRICE);
                    tokens.get(0).add(putin);
                }
            } else if (token.type() == Token.Type.COMPARE) {
                if (myTokenizer.hasNext()) {
                    myTokenizer.next();
                    Token tokenNext = myTokenizer.current();
                    if (tokenNext.type() == Token.Type.INT) {
                        if (myTokenizer.hasNext()) {
                            myTokenizer.next();
                            Token tokenNextNext = myTokenizer.current();
                            if (tokenNextNext.type()== Token.Type.PRICE) {
                                Token putin = new Token(token.token()+"|"+tokenNext.token(),Token.Type.PRICE);
                                tokens.get(0).add(putin);
                            } else if (tokenNextNext.type()== Token.Type.ROOM) {
                                Token putin = new Token(token.token()+"|"+tokenNext.token()+"|"+tokenNextNext.token(),Token.Type.ROOM);
                                tokens.get(2).add(putin);
                            }
                        }
                    } else if (tokenNext.type() == Token.Type.PRICE) {
                        if (myTokenizer.hasNext()) {
                            myTokenizer.next();
                            Token tokenNextNext = myTokenizer.current();
                            if (tokenNextNext.type()== Token.Type.INT) {
                                Token putin = new Token(token.token()+"|"+tokenNextNext.token(),Token.Type.PRICE);
                                tokens.get(0).add(putin);
                            }
                        }
                    } else if (tokenNext.type() == Token.Type.ROOM) {
                        if (myTokenizer.hasNext()) {
                            myTokenizer.next();
                            Token tokenNextNext = myTokenizer.current();
                            if (tokenNextNext.type()== Token.Type.INT) {
                                Token putin = new Token(token.token()+"|"+tokenNextNext.token()+"|"+tokenNext.token(),Token.Type.ROOM);
                                tokens.get(0).add(putin);
                            }
                        }

                    }
                }
            }
            myTokenizer.next();
        }
        Token rest = new Token(myTokenizer._buffer.replaceAll(" ",""), Token.Type.ELSE);
        tokens.get(3).add(rest);
    }
}