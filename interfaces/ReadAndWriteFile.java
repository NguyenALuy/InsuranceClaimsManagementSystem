package interfaces;

import java.io.FileNotFoundException;

public interface ReadAndWriteFile {
    void readFromFile() throws FileNotFoundException;
    void writeToFile();
}
