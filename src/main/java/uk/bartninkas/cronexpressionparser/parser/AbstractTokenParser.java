package uk.bartninkas.cronexpressionparser.parser;

import com.google.common.collect.ImmutableList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractTokenParser implements TokenParser {

    public abstract int rangeStart();

    public abstract int rangeEnd();

    @Override
    public TokenParserResult parse(final String expr) {
        checkNotNull(expr);

        List<Token> list = parseToTokens(expr);
        Deque<Token> tokens = new ArrayDeque<>(list);
        Deque<List<TokenType>> controls = new ArrayDeque<>();
        controls.add(ImmutableList.of(TokenType.Star, TokenType.Number));

        TokenParserResult result = new TokenParserResult();
        result.setRangeStart(rangeStart());
        result.setRangeEnd(rangeEnd());

        TokenType prev = null;
        boolean forbidRangeExpr = false;
        Token currentToken;
        List<TokenType> currentControl;
        while ((currentToken = tokens.poll()) != null && (currentControl = controls.poll()) != null) {
            for (TokenType or : currentControl) {
                if (currentToken.getTokenType().equals(or)) {
                    if (TokenType.Star.equals(or)) {
                        result.setAny(true);
                        if (tokens.size() != 0) {
                            controls.add(ImmutableList.of(TokenType.Divide));
                        }
                    } else if (TokenType.Divide.equals(or)) {
                        forbidRangeExpr = true;
                        controls.add(ImmutableList.of(TokenType.Number));
                    } else if (TokenType.Minus.equals(or)) {
                        if(result.isAny() && forbidRangeExpr) {
                            //this combination should be forbidden
                            throw new ParserException("Expression '*/{number}-{number}' is not allowed.");
                        }
                        prev = TokenType.Minus;
                        controls.add(ImmutableList.of(TokenType.Number));
                        result.setType(TokenParserResult.Type.Range);
                    } else if (TokenType.Comma.equals(or)) {
                        prev = TokenType.Comma;
                        controls.add(ImmutableList.of(TokenType.Number));
                        result.setType(TokenParserResult.Type.List);
                    } else if (TokenType.Number.equals(or)) {
                        if(result.isAny() && currentToken.getValue().equals("0")) {
                            //this combination should be forbidden
                            throw new ParserException("Expression '*/0' is not allowed.");
                        }

                        result.addValue(currentToken.getValue());
                        if (tokens.size() != 0) {
                            if (prev == null) {
                                controls.add(ImmutableList.of(TokenType.Comma, TokenType.Minus));
                            } else if (prev.equals(TokenType.Comma)) {
                                controls.add(ImmutableList.of(TokenType.Comma));
                            }
                        }
                    }
                    break;
                }
            }
        }

        if(!tokens.isEmpty()) {
            throw new ParserException("Expression parsing failed due to not expected sequence.");
        }
        if(!controls.isEmpty()) {
            throw new ParserException("Expression parsing failed due to not supported syntax. Expected '" + controls.peek() + "' but not found.");
        }
        return result;
    }

    private List<Token> parseToTokens(final String expr) {
        List<Token> tokens = new ArrayList<>();
        char[] arr = expr.toCharArray();
        int len = arr.length;

        for (int i = 0; i < len; i++) {
            char ch = arr[i];

            if (TokenType.Star.getChar() == ch) {
                tokens.add(new Token(TokenType.Star));
            } else if (TokenType.Divide.getChar() == ch) {
                tokens.add(new Token(TokenType.Divide));
            } else if (TokenType.Comma.getChar() == ch) {
                tokens.add(new Token(TokenType.Comma));
            } else if (TokenType.Minus.getChar() == ch) {
                tokens.add(new Token(TokenType.Minus));
            } else {
                String value = parseNumber(arr, i, len);
                i = i + value.length() - 1;
                tokens.add(new Token(TokenType.Number, value));
            }
        }
        return tokens;
    }

    private String parseNumber(char[] arr, int beginning, int length) {
        int result = 0;
        for (int i = beginning; i < length; i++) {
            if (Character.isDigit(arr[i])) {
                result = result * 10 + (arr[i] - '0');
                if(result > rangeEnd()) {
                    throw new ParserException(
                            String.format("Number '%d' should be in range '%d' and '%d'.", result, rangeStart(), rangeEnd()));
                }
            } else {
                break;
            }
        }
        if(rangeStart() > result) {
            throw new ParserException(
                    String.format("Number '%d' should be in range '%d' and '%d'.", result, rangeStart(), rangeEnd()));
        }
        return String.valueOf(result);
    }
}
