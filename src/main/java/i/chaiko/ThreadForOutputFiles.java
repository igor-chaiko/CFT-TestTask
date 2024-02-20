package i.chaiko;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class ThreadForOutputFiles extends Thread {
    private final String flag;

    public ThreadForOutputFiles(String flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            String fileName = determineFileName();
            BlockingQueue<?> queue = determineQueue();

            boolean fileCreated = false;
            BufferedWriter bufferedWriter = null;

            while (true) {
                Object elem = queue.poll();
                if (elem != null) {
                    if (!fileCreated) {
                        File filePath = createFilePath(fileName);
                        FileWriter fileWriter = new FileWriter(filePath, Options.appendToExistingFile);
                        bufferedWriter = new BufferedWriter(fileWriter);
                        fileCreated = true;
                    }

                    writeElemToFile(bufferedWriter, String.valueOf(elem));
                }

                if (ValueDeterminant.everythingIsRead && elem == null) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String determineFileName() {
        String res;

        switch (this.flag) {
            case "Integers" -> {
                res = Options.prefix != null ? Options.prefix + "integers.txt" : "integers.txt";
            }

            case "Floats" -> {
                res = Options.prefix != null ? Options.prefix + "floats.txt" : "floats.txt";
            }

            case "Strings" -> {
                res = Options.prefix != null ? Options.prefix + "strings.txt" : "strings.txt";
            }

            default -> throw new IllegalArgumentException("Invalid flag: " + flag);
        }

        return res;
    }

    private BlockingQueue<?> determineQueue() {
        switch (this.flag) {
            case "Integers" -> {
                return ValueDeterminant.integerBlockingQueue;
            }

            case "Floats" -> {
                return ValueDeterminant.floatBlockingQueue;
            }

            case "Strings" -> {
                return ValueDeterminant.stringBlockingQueue;
            }

            default -> throw new IllegalArgumentException("Invalid flag: " + flag);
        }
    }

    private File createFilePath(String fileName) {
        return Options.directoryPath != null ?
                new File(Options.directoryPath + "/" + fileName) :
                new File(fileName);
    }

    private void writeElemToFile(BufferedWriter bufferedWriter, String elem) throws IOException {
        bufferedWriter.write(elem);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
}
