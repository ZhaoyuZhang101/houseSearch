package Tokenizer;

public class Token {
    
	public enum Type {NULL, PRICE, COMPARE, INT, TYPE, ROOM, ELSE}
    private String _token = "";
    private Type _type = Type.NULL;
    
    public Token(String token, Type type) {
        _token = token;
        _type = type;
    }
    
    public String token() {
        return _token;
    }
    
    public Type type() {
        return _type;
    }
}
