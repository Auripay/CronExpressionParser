package uk.bartninkas.cronexpressionparser.printer;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TablePrinterTest {

    @InjectMocks
    private TablePrinter underTest;

    @Test
    public void should_generate_empty_table_representation() {
        String result = underTest.tableAsString();
        assertEquals("", result);
    }

    @Test
    public void should_generate_valid_table() {
        underTest.addValues("one", ImmutableList.of("1"));
        underTest.addValues("two", ImmutableList.of("1", "2"));
        String result = underTest.tableAsString();
        assertEquals("one           1  \ntwo           1  2  \n", result);
    }

    @Test
    public void should_generate_valid_table_with_only_14_column_trim_others() {
        underTest.addValues(
                "one", IntStream.rangeClosed(0, 15).mapToObj(String::valueOf).collect(Collectors.toList()));
        String result = underTest.tableAsString();
        assertEquals("one           0  1  2  3  4  5  6  7  8  9  10 11 12 13 \n", result);
    }
}