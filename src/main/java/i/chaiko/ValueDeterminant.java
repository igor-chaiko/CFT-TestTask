package i.chaiko;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ValueDeterminant {
    static BlockingQueue<String> stringBlockingQueue = new LinkedBlockingQueue<>(1000);
    static BlockingQueue<Integer> integerBlockingQueue = new LinkedBlockingQueue<>(1000);
    static BlockingQueue<Float> floatBlockingQueue = new LinkedBlockingQueue<>(1000);
    static boolean everythingIsRead = false;

    synchronized static void valueDetermine(String value) throws InterruptedException {
        Integer intValue;
        Float floatValue;

        try {
            intValue = Integer.parseInt(value);
            ValueDeterminant.integerBlockingQueue.put(intValue);

        } catch (NumberFormatException e) {

            try {
                floatValue = Float.parseFloat(value);
                ValueDeterminant.floatBlockingQueue.put(floatValue);

            } catch (NumberFormatException ex) {
                ValueDeterminant.stringBlockingQueue.put(value);

            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
