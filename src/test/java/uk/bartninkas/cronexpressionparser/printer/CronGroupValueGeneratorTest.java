package uk.bartninkas.cronexpressionparser.printer;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.bartninkas.cronexpressionparser.parser.TokenParserResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CronGroupValueGeneratorTest {

    @InjectMocks
    private CronGroupValueGenerator underTest;

    @Test
    public void should_generate_value() {
        TokenParserResult tokenParserResult = new TokenParserResult();
        tokenParserResult.setType(TokenParserResult.Type.Value);
        tokenParserResult.addValue("1");

        List<String> result = underTest.generate(tokenParserResult);
        assertIterableEquals(result, ImmutableList.of("1"));
    }

    @Test
    public void should_generate_value_from_range() {
        TokenParserResult tokenParserResult = new TokenParserResult();
        tokenParserResult.setType(TokenParserResult.Type.Range);
        tokenParserResult.setRangeStart(0);
        tokenParserResult.setRangeEnd(10);
        tokenParserResult.addValue("1");
        tokenParserResult.addValue("5");

        List<String> result = underTest.generate(tokenParserResult);
        assertIterableEquals(result, ImmutableList.of("1", "2", "3", "4", "5"));
    }

    @Test
    public void should_generate_value_from_list() {
        TokenParserResult tokenParserResult = new TokenParserResult();
        tokenParserResult.setType(TokenParserResult.Type.List);
        tokenParserResult.setRangeStart(0);
        tokenParserResult.setRangeEnd(10);
        tokenParserResult.addValue("2");
        tokenParserResult.addValue("3");
        tokenParserResult.addValue("4");

        List<String> result = underTest.generate(tokenParserResult);
        assertIterableEquals(result, ImmutableList.of("2", "3", "4"));
    }

    @Test
    public void should_generate_value_from_list_with_star() {
        TokenParserResult tokenParserResult = new TokenParserResult();
        tokenParserResult.setAny(true);
        tokenParserResult.setType(TokenParserResult.Type.List);
        tokenParserResult.setRangeStart(0);
        tokenParserResult.setRangeEnd(8);
        tokenParserResult.addValue("2");
        tokenParserResult.addValue("5");

        List<String> result = underTest.generate(tokenParserResult);
        assertIterableEquals(result, ImmutableList.of("0", "2", "4", "5", "6", "8"));
    }

    @Test
    public void should_fail_when_star_and_range_set() {
        TokenParserResult tokenParserResult = new TokenParserResult();
        tokenParserResult.setAny(true);
        tokenParserResult.setType(TokenParserResult.Type.Range);
        tokenParserResult.setRangeStart(0);
        tokenParserResult.setRangeEnd(8);
        tokenParserResult.addValue("2");
        tokenParserResult.addValue("5");

        Exception exception = assertThrows(IllegalStateException.class,
                () -> underTest.generate(tokenParserResult));
        assertEquals(
                "Expression '*'/{number}-{number} is not supported.",
                exception.getMessage());
    }
}