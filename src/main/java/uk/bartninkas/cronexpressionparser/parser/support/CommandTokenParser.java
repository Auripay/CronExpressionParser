package uk.bartninkas.cronexpressionparser.parser.support;

import uk.bartninkas.cronexpressionparser.parser.TokenParser;
import uk.bartninkas.cronexpressionparser.parser.TokenParserResult;

import static com.google.common.base.Preconditions.checkNotNull;

public class CommandTokenParser implements TokenParser {

    @Override
    public TokenParserResult parse(String expr) {
        checkNotNull(expr);
        TokenParserResult result = new TokenParserResult();
        result.setCommand(true);
        result.addValue(expr);
        return result;
    }
}
