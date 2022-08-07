package uk.bartninkas.cronexpressionparser.parser.group;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CronGroupsParserTest {

    @InjectMocks
    private CronGroupsParser underTest;

    @Test
    public void should_parse_valid_expr() {
        Group result = underTest.parse("* * * * * *");
        assertTrue(result.validate());
    }

    @Test
    public void should_parse_valid_expr_with_whitespaces() {
        Group result = underTest.parse("*    * * * *    *");
        assertTrue(result.validate());
    }

    @Test
    public void should_parse_valid_expr_with_whitespaces_complex() {
        Group result = underTest.parse("*/1    */2 */3 */4 */5    command1");
        assertTrue(result.validate());
        assertTrue(result.getResult(GroupType.Minutes).isAny());
        assertEquals(1, result.getResult(GroupType.Minutes).getValues().size());
        assertTrue(result.getResult(GroupType.Hours).isAny());
        assertEquals(1, result.getResult(GroupType.Hours).getValues().size());
        assertTrue(result.getResult(GroupType.DaysMonth).isAny());
        assertEquals(1, result.getResult(GroupType.DaysMonth).getValues().size());
        assertTrue(result.getResult(GroupType.Months).isAny());
        assertEquals(1, result.getResult(GroupType.Months).getValues().size());
        assertTrue(result.getResult(GroupType.DaysWeek).isAny());
        assertEquals(1, result.getResult(GroupType.DaysWeek).getValues().size());
        assertTrue(result.getResult(GroupType.Command).isCommand());
        assertEquals(1, result.getResult(GroupType.Command).getValues().size());
    }

    @Test
    public void should_parse_and_fail_not_enough_groups() {
        Group result = underTest.parse("*/1    */2 */3 */4 */5 ");
        assertFalse(result.validate());
    }
}