package uk.bartninkas.cronexpressionparser.parser.support;

import uk.bartninkas.cronexpressionparser.parser.AbstractTokenParser;

public class MinutesTokenParser extends AbstractTokenParser {

    @Override
    public int rangeStart() {
        return 0;
    }

    @Override
    public int rangeEnd() {
        return 59;
    }
}
