package uk.bartninkas.cronexpressionparser.parser.group;

public enum GroupType {
    Minutes(0),
    Hours(1),
    DaysMonth(2),
    Months(3),
    DaysWeek(4),
    Command(5);

    private int position;

    GroupType(int position) {
        this.position = position;
    }

    public static GroupType position(int position) {
        for(GroupType groupType : values()) {
            if(groupType.position == position) return groupType;
        }
        throw new IllegalStateException("No group available by supplied position: " + position);
    }
}
