package uk.bartninkas.cronexpressionparser.printer;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TablePrinter {

    private final static int MAX_COLUMN_COUNT = 14;

    private StringBuilder buf;

    public TablePrinter() {
        this.buf = new StringBuilder();
    }

    public void addValues(String name, List<String> values) {
        checkNotNull(name);
        checkNotNull(values);

        buf.append(String.format("%1$-14s",name));
        for(int i = 0; i < MAX_COLUMN_COUNT && i < values.size(); i++) {
            buf.append(String.format("%1$-3s",values.get(i)));
        }
        buf.append("\n");
    }

    public String tableAsString() {
        return buf.toString();
    }
}
