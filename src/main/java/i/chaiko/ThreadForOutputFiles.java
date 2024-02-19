package i.chaiko;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ThreadForOutputFiles extends Thread {
    private final String flag;

    public ThreadForOutputFiles(String flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        switch (this.flag) {
            case "Integers" -> {
                try {
                    String fileName = "integers.txt";
                    if (Options.prefix != null) {
                        fileName = Options.prefix + fileName;
                    }

                    boolean intsFileCreated = false;
                    BufferedWriter bufferedWriter = null;

                    while (true) {
                        Integer elem = ValueDeterminant.integerBlockingQueue.poll();
                        if (elem != null) {
                            File filePath;
                            if (!intsFileCreated) {
                                if (Options.directoryPath != null) {
                                    filePath = new File(Options.directoryPath + "/" + fileName);
                                } else {
                                    filePath = new File(fileName);
                                }
                                FileWriter fileWriter = new FileWriter(filePath, Options.appendToExistingFile);
                                bufferedWriter = new BufferedWriter(fileWriter);
                                intsFileCreated = true;
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

            case "Floats" -> {
                try {
                    String fileName = "floats.txt";
                    if (Options.prefix != null) {
                        fileName = Options.prefix + fileName;
                    }

                    boolean floatsFileCreated = false;
                    BufferedWriter bufferedWriter = null;

                    while (true) {
                        Float elem = ValueDeterminant.floatBlockingQueue.poll();
                        if (elem != null) {
                            File filePath;
                            if (!floatsFileCreated) {
                                if (Options.directoryPath != null) {
                                    filePath = new File(Options.directoryPath + "/" + fileName);
                                } else {
                                    filePath = new File(fileName);
                                }
                                FileWriter fileWriter = new FileWriter(filePath, Options.appendToExistingFile);
                                bufferedWriter = new BufferedWriter(fileWriter);
                                floatsFileCreated = true;
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

            case "Strings" -> {
                try {
                    String fileName = "strings.txt";
                    if (Options.prefix != null) {
                        fileName = Options.prefix + fileName;
                    }

                    boolean stringsFileCreated = false;
                    BufferedWriter bufferedWriter = null;

                    while (true) {
                        String elem = ValueDeterminant.stringBlockingQueue.poll();
                        if (elem != null) {
                            File filePath;
                            if (!stringsFileCreated) {
                                if (Options.directoryPath != null) {
                                    filePath = new File(Options.directoryPath + "/" + fileName);
                                } else {
                                    filePath = new File(fileName);
                                }
                                FileWriter fileWriter = new FileWriter(filePath, Options.appendToExistingFile);
                                bufferedWriter = new BufferedWriter(fileWriter);
                                stringsFileCreated = true;
                            }

                            writeElemToFile(bufferedWriter, elem);
                        }

                        if (ValueDeterminant.everythingIsRead && elem == null) {
                            break;
                        }
                    }

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void writeElemToFile(BufferedWriter bufferedWriter, String elem) throws IOException {
        bufferedWriter.write(elem);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
}
