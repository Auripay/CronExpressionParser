package uk.bartninkas.cronexpressionparser;

import uk.bartninkas.cronexpressionparser.parser.group.CronGroupsParser;
import uk.bartninkas.cronexpressionparser.parser.group.Group;
import uk.bartninkas.cronexpressionparser.parser.group.GroupType;
import uk.bartninkas.cronexpressionparser.printer.CronGroupValueGenerator;
import uk.bartninkas.cronexpressionparser.printer.TablePrinter;

import static com.google.common.base.Preconditions.checkNotNull;

public class CronJob {

    private final CronGroupsParser parser;
    private final CronGroupValueGenerator extractValues;

    public CronJob() {
        this.parser = new CronGroupsParser();
        this.extractValues = new CronGroupValueGenerator();
    }

    public String execute(String expr) {
        checkNotNull(expr);
        Group cronGroups = parser.parse(expr);
        if(!cronGroups.validate()) {
            throw new IllegalStateException("Supplied expression validation failed. Check supplied arguments.");
        }

        TablePrinter tablePrinter = new TablePrinter();

        tablePrinter.addValues("minute", extractValues.generate(cronGroups.getResult(GroupType.Minutes)));
        tablePrinter.addValues("hour", extractValues.generate(cronGroups.getResult(GroupType.Hours)));
        tablePrinter.addValues("day of month", extractValues.generate(cronGroups.getResult(GroupType.DaysMonth)));
        tablePrinter.addValues("month", extractValues.generate(cronGroups.getResult(GroupType.Months)));
        tablePrinter.addValues("day of week", extractValues.generate(cronGroups.getResult(GroupType.DaysWeek)));
        tablePrinter.addValues("command", extractValues.generate(cronGroups.getResult(GroupType.Command)));
        return tablePrinter.tableAsString();
    }

    public static void main(String... args) {
        if(args.length != 1 || args[0] == null) {
            throw new IllegalArgumentException("No expression supplied.");
        }

        CronJob cronJob = new CronJob();
        System.out.println("Cron Job expression: " + args[0] + " \n");
        System.out.println(cronJob.execute(args[0]));
    }
}
