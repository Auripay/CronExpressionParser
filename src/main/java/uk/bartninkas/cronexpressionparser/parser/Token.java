package uk.bartninkas.cronexpressionparser.parser;

import javax.annotation.Nullable;

public class Token {
    private final TokenType tokenType;
    private String value;

    public Token(TokenType tokenType, @Nullable String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    public Token(TokenType tokenType) {
        this(tokenType, null);
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    @Nullable
    public String getValue() {
        return value;
    }
}
