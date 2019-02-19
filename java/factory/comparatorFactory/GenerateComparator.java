package factory.comparatorFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class GenerateComparator {
    private static final GenerateComparator comparator = new GenerateComparator();

    private HashMap<String, Comparator<Object>>  hashComp;
    private GenerateComparator() {
        hashComp = new HashMap<>();
        //конфигурационный файл
        InputStream reader = this.getClass().getResourceAsStream("ComparatorName.txt");
        assert reader != null;

        Scanner scanner = new Scanner(reader);
            StringBuilder key = new StringBuilder();
            StringBuilder comp = new StringBuilder();
            while (scanner.hasNextLine()) {
                key.append(scanner.nextLine());
                comp.append(scanner.nextLine());
                try {
                    Class<?> clazz = Class.forName(comp.toString());
                    Constructor<?> constructor = clazz.getConstructor();
                    Object instance = constructor.newInstance();
                    hashComp.put(key.toString(), (Comparator<Object>)instance);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                key.delete(0, key.length());
                comp.delete(0, comp.length());
            }
    }

    public static boolean isKeyInDatabase(String key) {
        return comparator.hashComp.containsKey(key);
    }

    public static Comparator<Object> getComparator(String key) {
        return comparator.hashComp.get(key);
    }
}
