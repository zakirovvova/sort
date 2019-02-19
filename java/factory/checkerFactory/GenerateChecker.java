package factory.checkerFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Scanner;

public class GenerateChecker {
    private static final GenerateChecker checker = new GenerateChecker();
    private HashMap<String, InputChecker> hashChecker;
    private GenerateChecker() {
        hashChecker = new HashMap<>();
            //конфигурационный файл
            InputStream reader = this.getClass().getResourceAsStream("CheckerName.txt");
            assert null == reader;

            Scanner scanner = new Scanner(reader);
            StringBuilder key = new StringBuilder();
            StringBuilder checker = new StringBuilder();
            while (scanner.hasNextLine()) {
                key.append(scanner.nextLine());
                checker.append(scanner.nextLine());
                try {
                    Class<?> clazz = Class.forName(checker.toString());
                    Constructor<?> constructor = clazz.getConstructor();
                    Object instance = constructor.newInstance();
                    hashChecker.put(key.toString(), (InputChecker) instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                key.delete(0, key.length());
                checker.delete(0, checker.length());
            }
    }
    public static boolean isKeyInDatabase(String key) {
        return checker.hashChecker.containsKey(key);
    }

    public static InputChecker getChecker(String key) {
        return checker.hashChecker.get(key);
    }
}