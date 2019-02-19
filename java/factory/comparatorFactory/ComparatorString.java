package factory.comparatorFactory;

import java.util.Comparator;

public class ComparatorString implements Comparator<Object> {
    public int compare(Object str1, Object str2) {
        return ((String)str1).compareTo(((String)str2));
    }
}
