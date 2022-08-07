package uk.bartninkas.cronexpressionparser.parser.group;

import com.google.common.collect.Maps;
import uk.bartninkas.cronexpressionparser.parser.TokenParser;
import uk.bartninkas.cronexpressionparser.parser.TokenParserResult;
import uk.bartninkas.cronexpressionparser.parser.support.*;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class Group {

    private final static int EXPECTED_GROUP_COUNT = 6;

    private final Map<GroupType, TokenParserResult> groups;
    private final Map<GroupType, TokenParser> tokenParserMap;

    public Group() {
        groups = Maps.newConcurrentMap();
        tokenParserMap = Maps.newConcurrentMap();

        tokenParserMap.put(GroupType.Minutes, new MinutesTokenParser());
        tokenParserMap.put(GroupType.Hours, new HoursTokenParser());
        tokenParserMap.put(GroupType.DaysMonth, new DaysMonthTokenParser());
        tokenParserMap.put(GroupType.Months, new MonthsTokenParser());
        tokenParserMap.put(GroupType.DaysWeek, new DaysTokenParser());
        tokenParserMap.put(GroupType.Command, new CommandTokenParser());
    }

    public void put(GroupType groupType, String expr) {
        checkNotNull(groupType);
        checkNotNull(expr);

        if(!tokenParserMap.containsKey(groupType)) {
            throw new GroupException(String.format("No parser associated with group: %s.", groupType));
        }

        groups.put(groupType, tokenParserMap.get(groupType).parse(expr));
    }

    public boolean validate() {
        return groups.size() == EXPECTED_GROUP_COUNT;
    }

    public TokenParserResult getResult(GroupType groupType) {
        checkNotNull(groupType);

        if(!tokenParserMap.containsKey(groupType)) {
            throw new GroupException(String.format("No parser associated with group: %s.", groupType));
        }
        return groups.get(groupType);
    }

    @Override
    public String toString() {
        return "Groups { " + groups.toString() + " }";
    }
}
