package i.chaiko;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] arguments) throws InterruptedException {
        String[] args = new String[] {"in1.txt", "in2.txt", "in3.txt", "-o", "src/storage", "-p", "ok_", "-s"};
        ArrayList<String> fileNames = parseArgs(args);
        Main.entryPoint(fileNames);
    }

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

        Main.statistic();
    }

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

    private static void statistic() {
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
            System.out.println("avg Integer:" + ((double)Statistic.sumOfIntegers / (double)Statistic.integersCount) );
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
