package uk.bartninkas.cronexpressionparser.parser.support;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.bartninkas.cronexpressionparser.parser.TokenParserResult;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommandTokenParserTest {

    @InjectMocks
    private CommandTokenParser underTest;

    @Test
    public void should_parse_valid_command() {
        TokenParserResult result = underTest.parse("find . -name secure_place");

        assertTrue(result.isCommand());
        assertFalse(result.isAny());
        assertEquals(TokenParserResult.Type.Value, result.getType());
        assertEquals("find . -name secure_place", result.getValues().get(0));
    }
}