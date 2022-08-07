package uk.bartninkas.cronexpressionparser.parser.group;

import static com.google.common.base.Preconditions.checkNotNull;

public class CronGroupsParser {

    public Group parse(final String expr) {
        checkNotNull(expr);

        Group groups = new Group();
        char[] exprChar = expr.trim().toCharArray(); //no white space at the beginning and at the end of the expr

        StringBuilder buf = new StringBuilder();
        int group = 0;
        boolean prevWhitespace = false;
        for(int i = 0; i < exprChar.length; i++) {
            char ch = exprChar[i];
            boolean whitespace = Character.isWhitespace(ch);

            if(whitespace && !prevWhitespace && group < 5) {
                groups.put(GroupType.position(group), buf.toString());
                buf = new StringBuilder();
                group++;
            } else if(whitespace && group < 5) {
                //skip
            } else {
                buf.append(ch);
            }
            prevWhitespace = whitespace;
        }
        groups.put(GroupType.position(group), buf.toString());

        return groups;
    }
}
