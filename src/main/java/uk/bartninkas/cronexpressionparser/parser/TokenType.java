package uk.bartninkas.cronexpressionparser.parser;

public enum TokenType {

    Star('*'),
    Minus('-'),
    Divide('/'),
    Comma(','),
    Number();

    private boolean set;
    private char ch;

    TokenType(char ch) {
        set = true;
        this.ch = ch;
    }

    TokenType() {}

    public char getChar() {
        if(!set) throw new IllegalStateException("No char associated with " + name());
        return ch;
    }
}
