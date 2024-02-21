package i.chaiko;

public class Statistic {
    static boolean shortStatistic = false;
    static int integersCount = 0;
    static int floatsCount = 0;
    static int stringsCount = 0;
    static boolean fullStatistic = false;
    static Long minInteger = null;
    static Long maxInteger = null;
    static Long sumOfIntegers = null;
    static Float minFloat = null;
    static Float maxFloat = null;
    static Float sumOfFloats = null;
    static Integer minString = null;
    static Integer maxString = null;

    /**
     * класс для вывода статистики.
     */
    static void printStatistics() {
        if (Statistic.shortStatistic) {
            System.out.println("-----------------------------------\nShort statistics:");
            System.out.println("number of integers: " + Statistic.integersCount);
            System.out.println("number of floats: " + Statistic.floatsCount);
            System.out.println("number of strings: " + Statistic.stringsCount);
            System.out.println("-----------------------------------");
        }

        if (Statistic.fullStatistic) {
            System.out.println("-----------------------------------\nFull statistics:");
            System.out.println("For numbers:");
            System.out.println("number of integers: " + Statistic.integersCount);
            System.out.println("min Integer: " + Statistic.minInteger);
            System.out.println("max Integer: " + Statistic.maxInteger);
            System.out.println("sum of Integers: " + Statistic.sumOfIntegers);
            System.out.println("avg Integer:" + (Statistic.sumOfIntegers / Statistic.integersCount) );
            System.out.println("------------------------------");
            System.out.println("number of floats: " + Statistic.floatsCount);
            System.out.println("min Float: " + Statistic.minFloat);
            System.out.println("max Float: " + Statistic.maxFloat);
            System.out.println("sum of Floats: " + Statistic.sumOfFloats);
            System.out.println("avg Float: " + (Statistic.sumOfFloats / (double)Statistic.floatsCount) );
            System.out.println("------------------------------\nFor strings:");
            System.out.println("number of strings: " + Statistic.stringsCount);
            System.out.println("max string's length: " + Statistic.maxString);
            System.out.println("min string's length: " + Statistic.minString);
            System.out.println("-----------------------------------");
        }
    }
}
