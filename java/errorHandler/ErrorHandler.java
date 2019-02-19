package errorHandler;

public class ErrorHandler {
    public static void noArguments() {
        System.err.println("You have no argument.");
    }
    public static void keyDataOverride() {
        System.err.println("Re-entered data validation key, selected first available.");
    }
    public static void keySortOverride() {
        System.err.println("Re-entered sort validation key, selected first available.");
    }
    public static void noDataKeys() {
        System.err.println("No data keys.");
    }
    public static void noOutputFileName() {
        System.err.println("No output file name.");
    }
    public static void noFileForReading() {
        System.err.println("No file for reading.");
    }
    public static  void missingFile(String name) {
        System.err.println("file " + name + " does not exist.");
    }
    public static void noFilesToRead() {
        System.err.println("No files to read.");
    }
    public static void incorrectData(String str) {
        System.err.println("Data " + str + "incorrect.");
    }
}