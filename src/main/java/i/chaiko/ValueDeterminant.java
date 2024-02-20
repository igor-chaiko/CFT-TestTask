package i.chaiko;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ValueDeterminant {
    static BlockingQueue<String> stringBlockingQueue = new LinkedBlockingQueue<>(1000);
    static BlockingQueue<Integer> integerBlockingQueue = new LinkedBlockingQueue<>(1000);
    static BlockingQueue<Float> floatBlockingQueue = new LinkedBlockingQueue<>(1000);
    static boolean everythingIsRead = false;

    static void valueDetermine(String value) throws InterruptedException {
        if (value.matches("-?\\d+")) {
            int intValue = Integer.parseInt(value);
            ValueDeterminant.integerBlockingQueue.put(intValue);
            Statistic.integersCount++;

            if (Statistic.minInteger == null || Statistic.minInteger > intValue) {
                Statistic.minInteger = intValue;
            }

            if (Statistic.maxInteger == null || Statistic.maxInteger < intValue) {
                Statistic.maxInteger = intValue;
            }

            if (Statistic.sumOfIntegers == null) {
                Statistic.sumOfIntegers = intValue;
            } else {
                Statistic.sumOfIntegers += intValue;
            }

        } else if (value.matches("-?\\d+(\\.\\d+)?([eE][-+]?\\d+)?")) {
            float floatValue = Float.parseFloat(value);
            ValueDeterminant.floatBlockingQueue.put(floatValue);
            Statistic.floatsCount++;

            if (Statistic.minFloat == null || Statistic.minFloat > floatValue) {
                Statistic.minFloat = floatValue;
            }

            if (Statistic.maxFloat == null || Statistic.maxFloat < floatValue) {
                Statistic.maxFloat = floatValue;
            }

            if (Statistic.sumOfFloats == null) {
                Statistic.sumOfFloats = floatValue;
            } else {
                Statistic.sumOfFloats += floatValue;
            }

        } else {
            ValueDeterminant.stringBlockingQueue.put(value);
            Statistic.stringsCount++;

            if (Statistic.minString == null || Statistic.minString > value.length()) {
                Statistic.minString = value.length();
            }

            if (Statistic.maxString == null || Statistic.maxString < value.length()) {
                Statistic.maxString = value.length();
            }

        }
    }
}
