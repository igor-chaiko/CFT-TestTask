package i.chaiko;

import java.io.File;
import java.util.ArrayList;

/**
 * Управляющий класс.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> fileNames = parseArgs(args);
        Main.entryPoint(fileNames);
    }

    /**
     * начинаем работать с файлами, разобравшись с аргументами.
     * @param fileNames имена входных файлов.
     * @throws InterruptedException исключение на случай прерывания потока.
     */
    private static void entryPoint(ArrayList<String> fileNames) throws InterruptedException {
        final ArrayList<ThreadForInputFiles> inputTreadList = new ArrayList<>();
        final String[] dataTypes = new String[] {"Integers", "Floats", "Strings"};

        for (String fileName : fileNames) {
            ThreadForInputFiles thread = new ThreadForInputFiles(fileName);
            thread.start();
            inputTreadList.add(thread);
        }

        for (String type : dataTypes) {
            ThreadForOutputFiles thread = new ThreadForOutputFiles(type);
            thread.start();
        }

        for (ThreadForInputFiles thread : inputTreadList) {
            thread.join();
        }
        ValueDeterminant.everythingIsRead = true;

        Statistic.printStatistics();
    }

    /**
     * Парсим аргументы, выставляя соответствующие флаги.
     * @param args аргументы main.
     * @return имена входных файлов.
     */
    private static ArrayList<String> parseArgs(String[] args) {
        ArrayList<String> inputFiles = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o" -> {
                    if (i+1 >= args.length) {
                        break;
                    }

                    File directory = new File(args[i+1]);

                    if (directory.exists() && directory.isDirectory()) {
                        Options.directoryPath = args[++i];
                    }
                }

                case "-p" -> {
                    if (i+1 >= args.length) {
                        break;
                    }

                    Options.prefix = args[++i];
                }

                case "-a" -> {
                    Options.appendToExistingFile = true;
                }

                case "-s" -> {
                    Statistic.shortStatistic = true;
                }

                case "-f" -> {
                    Statistic.fullStatistic = true;
                }

                default -> {
                    inputFiles.add(args[i]);
                }
            }
        }

        return inputFiles;
    }
}
