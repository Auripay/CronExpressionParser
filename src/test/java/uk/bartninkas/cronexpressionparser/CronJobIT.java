package uk.bartninkas.cronexpressionparser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.bartninkas.cronexpressionparser.parser.ParserException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CronJobIT {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void should_parse_and_generate_valid_table_based_on_example() {
        CronJob.main("*/15 0 1,15 * 1-5 /usr/bin/find");
        assertEquals(
                "Cron Job expression: */15 0 1,15 * 1-5 /usr/bin/find \n" +
                        "\n" +
                        "minute        0  15 30 45 \n" +
                        "hour          0  \n" +
                        "day of month  1  15 \n" +
                        "month         1  2  3  4  5  6  7  8  9  10 11 12 \n" +
                        "day of week   1  2  3  4  5  \n" +
                        "command       /usr/bin/find\n" +
                        "\n",
                outputStreamCaptor.toString());
    }


    @Test
    public void should_parse_and_generate_valid_table() {
        CronJob.main("* * * * * /find . -name secure");
        assertEquals(
                "Cron Job expression: * * * * * /find . -name secure \n" +
                        "\n" +
                        "minute        0  1  2  3  4  5  6  7  8  9  10 11 12 13 \n" +
                        "hour          0  1  2  3  4  5  6  7  8  9  10 11 12 13 \n" +
                        "day of month  1  2  3  4  5  6  7  8  9  10 11 12 13 14 \n" +
                        "month         1  2  3  4  5  6  7  8  9  10 11 12 \n" +
                        "day of week   0  1  2  3  4  5  6  \n" +
                        "command       /find . -name secure\n" +
                        "\n",
                outputStreamCaptor.toString());
    }

    @Test
    public void should_fail_arg_value_empty() {
        Exception exception = assertThrows(ParserException.class,
                () -> CronJob.main(""));
        assertEquals(
                "Expression parsing failed due to not supported syntax. Expected '[Star, Number]' but not found.",
                exception.getMessage());
    }

    @Test
    public void should_fail_arg_empty() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                CronJob::main);
        assertEquals(
                "No expression supplied.",
                exception.getMessage());
    }

    @Test
    public void should_fail_wrong_arg() {
        Exception exception = assertThrows(IllegalStateException.class,
                () -> CronJob.main("*"));
        assertEquals(
                "Supplied expression validation failed. Check supplied arguments.",
                exception.getMessage());
    }
}