package uk.bartninkas.cronexpressionparser.printer;

import com.google.common.collect.Lists;
import uk.bartninkas.cronexpressionparser.parser.TokenParserResult;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkNotNull;

public class CronGroupValueGenerator {

    public List<String> generate(TokenParserResult result) {
        checkNotNull(result);
        if(result.isAny() && TokenParserResult.Type.Range.equals(result.getType())) {
            throw new IllegalStateException("Expression '*'/{number}-{number} is not supported.");
        }

        List<String> print = Lists.newArrayList();
        if (TokenParserResult.Type.Value.equals(result.getType())) {
            if (result.isAny()) {
                int value = result.getValues().isEmpty() ? 1 : Integer.parseInt(result.getValues().get(0));
                for (int i = result.getRangeStart(); i <= result.getRangeEnd(); i = i + value) {
                    print.add(String.valueOf(i));
                }
            } else {
                print.add(result.getValues().get(0));
            }
        } else if (TokenParserResult.Type.Range.equals(result.getType())) {
            //no need syntax '*/0-9' not supported
            IntStream.rangeClosed(
                            Integer.parseInt(result.getValues().get(0)),
                            Integer.parseInt(result.getValues().get(1)))
                    .forEach(it -> print.add(String.valueOf(it)));
        } else if (TokenParserResult.Type.List.equals(result.getType())) {
            if (result.isAny()) {
                for (String value : result.getValues()) {
                    for (int i = result.getRangeStart(); i <= result.getRangeEnd(); i = i + Integer.parseInt(value)) {
                        print.add(String.valueOf(i));
                    }
                }
            } else {
                print.addAll(result.getValues());
            }
        }

        if(print.size() != 1) {
            Set<String> deduplicate = new TreeSet<>(new Comparator<String>() {

                @Override
                public int compare(String s1, String s2) {
                    return Integer.parseInt(s1) - Integer.parseInt(s2);
                }
            });
            deduplicate.addAll(print);
            return deduplicate.stream().toList();
        }
        return print;
    }
}
