package uk.bartninkas.cronexpressionparser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CronJobTest {

    @InjectMocks
    private CronJob underTest;

    @Test
    public void should_parse_and_generate_valid_table() {
        String tableAsString = underTest.execute("* * * * * /find . -name secure");
        assertEquals(
                "minute        0  1  2  3  4  5  6  7  8  9  10 11 12 13 \n" +
                         "hour          0  1  2  3  4  5  6  7  8  9  10 11 12 13 \n" +
                         "day of month  1  2  3  4  5  6  7  8  9  10 11 12 13 14 \n" +
                         "month         1  2  3  4  5  6  7  8  9  10 11 12 \n" +
                         "day of week   0  1  2  3  4  5  6  \n" +
                         "command       /find . -name secure\n",
                tableAsString);
    }

    @Test
    public void should_parse_and_generate_valid_table_when_all_values() {
        String tableAsString = underTest.execute("1 2 3 4 5 /find . -name secure");
        assertEquals(
                "minute        1  \n" +
                         "hour          2  \n" +
                         "day of month  3  \n" +
                         "month         4  \n" +
                         "day of week   5  \n" +
                         "command       /find . -name secure\n",
                tableAsString);
    }

    @Test
    public void should_parse_and_generate_valid_table_when_all_values_in_list() {
        String tableAsString = underTest.execute("1,2,3 0,1,2 1,2,3 1,2 0,1 /find . -name secure");
        assertEquals(
                "minute        1  2  3  \n" +
                         "hour          0  1  2  \n" +
                         "day of month  1  2  3  \n" +
                         "month         1  2  \n" +
                         "day of week   0  1  \n" +
                         "command       /find . -name secure\n",
                tableAsString);
    }

    @Test
    public void should_parse_and_generate_valid_table_when_all_values_in_range() {
        String tableAsString = underTest.execute("1-3 0-2 1-3 1-2 0-1 /find . -name secure");
        assertEquals(
                "minute        1  2  3  \n" +
                        "hour          0  1  2  \n" +
                        "day of month  1  2  3  \n" +
                        "month         1  2  \n" +
                        "day of week   0  1  \n" +
                        "command       /find . -name secure\n",
                tableAsString);
    }

    @Test
    public void should_parse_and_generate_valid_table_when_values_mix() {
        String tableAsString = underTest.execute("*/1,2 */5 */12 */1,2,3 0-1 /find . -name secure");
        assertEquals(
                "minute        0  1  2  3  4  5  6  7  8  9  10 11 12 13 \n" +
                         "hour          0  5  10 15 20 \n" +
                         "day of month  1  13 25 \n" +
                         "month         1  2  3  4  5  6  7  8  9  10 11 12 \n" +
                         "day of week   0  1  \n" +
                         "command       /find . -name secure\n",
                tableAsString);
    }

    @Test
    public void should_parse_and_generate_valid_table_deduplicate_values() {
        String tableAsString = underTest.execute("*/1,2,3,4,5 */5 */12 */1,2,3 0-1 /find . -name secure");
        assertEquals(
                "minute        0  1  2  3  4  5  6  7  8  9  10 11 12 13 \n" +
                        "hour          0  5  10 15 20 \n" +
                        "day of month  1  13 25 \n" +
                        "month         1  2  3  4  5  6  7  8  9  10 11 12 \n" +
                        "day of week   0  1  \n" +
                        "command       /find . -name secure\n",
                tableAsString);
    }
}