package dataBase;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class FillerOutputFile {
    private Iterator dataBase;
    private FileWriter writer;
    public FillerOutputFile(Iterator dataBase, FileWriter writer) {
        this.dataBase = dataBase;
        this.writer = writer;
    }
    public void create() {
        while (dataBase.hasNext()) {
            try {
                writer.write(dataBase.next() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}