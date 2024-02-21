package i.chaiko;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * тут определяем тип элемента и кладем его в соотв очеред.
 * В них могут писать много потоков, но читают только 3, каждый пишет в свой файл.
 * Это сделано для того, чтобы при огромных файлах не переполнить оперативную память.
 * То есть в 1 момент времени в памяти будет не более 3000 элементов(можно менять вручную).
 */
public class ValueDeterminant {
    static BlockingQueue<String> stringBlockingQueue = new LinkedBlockingQueue<>(1000);
    static BlockingQueue<Long> integerBlockingQueue = new LinkedBlockingQueue<>(1000);
    static BlockingQueue<Float> floatBlockingQueue = new LinkedBlockingQueue<>(1000);
    static boolean everythingIsRead = false;

    /**
     * В первой версии использовал исключения, но это выглядело не особо читабельно, поэтому переделал через регулярки.
     * есть немного дублирование кода, но уже не успел декомпозировать.
     * @param value 1/3 типов.
     */
    static void valueDetermine(String value) throws InterruptedException {
        if (value.matches("-?\\d+")) {
            Long intValue = Long.parseLong(value);
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
