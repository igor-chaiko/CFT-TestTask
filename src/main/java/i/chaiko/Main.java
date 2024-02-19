package i.chaiko;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] arguments) throws InterruptedException {
        String[] args = new String[] {"in1.txt", "in2.txt", "in3.txt", "-o", "src/storage", "-p", "prefix_"};
        ArrayList<String> ar = parseArgs(args);
        Main.entryPoint(ar);
    }

    public static void entryPoint(ArrayList<String> args) throws InterruptedException {
        ArrayList<ThreadForInputFiles> threadList = new ArrayList<>();

        for (String fileName : args) {
            ThreadForInputFiles thread = new ThreadForInputFiles(fileName);
            thread.start();
            threadList.add(thread);
        }

        ThreadForOutputFiles intThread = new ThreadForOutputFiles("Integers");
        intThread.start();
        ThreadForOutputFiles floatThread = new ThreadForOutputFiles("Floats");
        floatThread.start();
        ThreadForOutputFiles stringThread = new ThreadForOutputFiles("Strings");
        stringThread.start();

        for (ThreadForInputFiles thread : threadList) {
            thread.join();
        }
        ValueDeterminant.everythingIsRead = true;

    }

    public static ArrayList<String> parseArgs(String[] args) {
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

                default -> {
                    inputFiles.add(args[i]);
                }
            }
        }

        return inputFiles;
    }
}
