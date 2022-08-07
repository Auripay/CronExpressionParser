package uk.bartninkas.cronexpressionparser.parser;

public interface TokenParser {

    TokenParserResult parse(final String expr);
}
