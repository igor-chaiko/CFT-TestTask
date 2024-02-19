package i.chaiko;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ThreadForInputFiles extends Thread {
    private final String fileName;

    public ThreadForInputFiles(String fileName) {
        this.fileName = fileName;
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("No such file!");
            return;
        }
    }

    @Override
    public void run() {
        try (FileReader fileReader = new FileReader(this.fileName)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                ValueDeterminant.valueDetermine(line);
            }

            return;

        } catch (IOException e) {

            System.out.println(e.getMessage());

        } catch (InterruptedException e) {

            throw new RuntimeException(e);
        }
    }
}
