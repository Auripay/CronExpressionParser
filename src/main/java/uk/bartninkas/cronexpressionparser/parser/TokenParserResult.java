package uk.bartninkas.cronexpressionparser.parser;

import com.google.common.collect.Lists;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TokenParserResult {
    public enum Type {
        List,
        Range,
        Value
    }

    private boolean isAny;
    private boolean isCommand;
    private Type type;
    private int rangeStart;
    private int rangeEnd;
    private final List<String> values;

    public TokenParserResult() {
        type = Type.Value;
        values = Lists.newArrayList();
    }

    public void setAny(boolean any) {
        isAny = any;
    }

    public void setType(Type type) {
        checkNotNull(type);
        this.type = type;
    }

    public void setCommand(boolean command) {
        isCommand = command;
    }

    public void setRangeStart(int rangeStart) {
        this.rangeStart = rangeStart;
    }

    public void setRangeEnd(int rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public void addValue(String value) {
        checkNotNull(type);
        values.add(value);
    }

    public boolean isAny() {
        return isAny;
    }

    public boolean isCommand() {
        return isCommand;
    }

    public Type getType() {
        return type;
    }

    public int getRangeStart() {
        return rangeStart;
    }

    public int getRangeEnd() {
        return rangeEnd;
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "TokenParserResult{" +
                "isAny=" + isAny +
                ", isCommand=" + isCommand +
                ", type=" + type +
                ", rangeStart=" + rangeStart +
                ", rangeEnd=" + rangeEnd +
                ", values=" + values +
                '}';
    }
}
