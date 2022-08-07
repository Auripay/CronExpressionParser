package uk.bartninkas.cronexpressionparser.parser.support;

import uk.bartninkas.cronexpressionparser.parser.AbstractTokenParser;

public class DaysMonthTokenParser extends AbstractTokenParser {

    @Override
    public int rangeStart() {
        return 1;
    }

    @Override
    public int rangeEnd() {
        return 31;
    }
}
