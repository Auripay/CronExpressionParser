package uk.bartninkas.cronexpressionparser.parser.support;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.bartninkas.cronexpressionparser.parser.ParserException;
import uk.bartninkas.cronexpressionparser.parser.TokenParserResult;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HoursTokenParserTest {

    @InjectMocks
    private HoursTokenParser underTest;

    @Test
    public void should_parse_valid_star_command() {
        TokenParserResult result = underTest.parse("*");

        assertFalse(result.isCommand());
        assertTrue(result.isAny());
        assertTrue(result.getValues().isEmpty());
        assertEquals(0, result.getRangeStart());
        assertEquals(23, result.getRangeEnd());
        assertEquals(TokenParserResult.Type.Value, result.getType());
    }

    @Test
    public void should_parse_valid_list_command() {
        TokenParserResult result = underTest.parse("1,2");

        assertFalse(result.isCommand());
        assertFalse(result.isAny());
        assertEquals(2, result.getValues().size());
        assertEquals(0, result.getRangeStart());
        assertEquals(23, result.getRangeEnd());
        assertEquals(TokenParserResult.Type.List, result.getType());
    }

    @Test
    public void should_parse_valid_range_command() {
        TokenParserResult result = underTest.parse("1-2");

        assertFalse(result.isCommand());
        assertFalse(result.isAny());
        assertEquals(2, result.getValues().size());
        assertEquals(0, result.getRangeStart());
        assertEquals(23, result.getRangeEnd());
        assertEquals(TokenParserResult.Type.Range, result.getType());
    }

    @Test
    public void should_parse_valid_value_command() {
        TokenParserResult result = underTest.parse("5");

        assertFalse(result.isCommand());
        assertFalse(result.isAny());
        assertEquals(1, result.getValues().size());
        assertEquals(0, result.getRangeStart());
        assertEquals(23, result.getRangeEnd());
        assertEquals(TokenParserResult.Type.Value, result.getType());
    }

    @Test
    public void should_parse_valid_star_value_command() {
        TokenParserResult result = underTest.parse("*/5");

        assertFalse(result.isCommand());
        assertTrue(result.isAny());
        assertEquals(1, result.getValues().size());
        assertEquals(0, result.getRangeStart());
        assertEquals(23, result.getRangeEnd());
        assertEquals(TokenParserResult.Type.Value, result.getType());
    }

    @Test
    public void should_parse_valid_star_list_command() {
        TokenParserResult result = underTest.parse("*/1,2,3");

        assertFalse(result.isCommand());
        assertTrue(result.isAny());
        assertEquals(3, result.getValues().size());
        assertEquals(0, result.getRangeStart());
        assertEquals(23, result.getRangeEnd());
        assertEquals(TokenParserResult.Type.List, result.getType());
    }

    @Test
    public void should_parse_and_fail_not_finished_star() {
        Exception exception = assertThrows(ParserException.class,
                () -> underTest.parse("*/"));
        assertEquals(
                "Expression parsing failed due to not supported syntax. Expected '[Number]' but not found.",
                exception.getMessage());
    }

    @Test
    public void should_parse_and_fail_not_finished_star_range() {
        Exception exception = assertThrows(ParserException.class,
                () -> underTest.parse("*/1-"));
        assertEquals(
                "Expression '*/{number}-{number}' is not allowed.",
                exception.getMessage());
    }

    @Test
    public void should_parse_and_fail_not_finished_star_list() {
        Exception exception = assertThrows(ParserException.class,
                () -> underTest.parse("*/2,"));
        assertEquals(
                "Expression parsing failed due to not supported syntax. Expected '[Number]' but not found.",
                exception.getMessage());
    }

    @Test
    public void should_parse_and_fail_not_finished_list() {
        Exception exception = assertThrows(ParserException.class,
                () -> underTest.parse("2,3,"));
        assertEquals(
                "Expression parsing failed due to not supported syntax. Expected '[Number]' but not found.",
                exception.getMessage());
    }

    @Test
    public void should_parse_and_fail_not_finished_range() {
        Exception exception = assertThrows(ParserException.class,
                () -> underTest.parse("2-"));
        assertEquals(
                "Expression parsing failed due to not supported syntax. Expected '[Number]' but not found.",
                exception.getMessage());
    }

    @Test
    public void should_parse_and_fail_out_of_range_higher() {
        Exception exception = assertThrows(ParserException.class,
                () -> underTest.parse("32"));
        assertEquals(
                "Number '32' should be in range '0' and '23'.",
                exception.getMessage());
    }

    @Test
    public void should_parse_and_fail_with_star_divide_by_zero_expr() {
        Exception exception = assertThrows(ParserException.class,
                () -> underTest.parse("*/0"));
        assertEquals(
                "Expression '*/0' is not allowed.",
                exception.getMessage());
    }
}