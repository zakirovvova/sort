package factory.comparatorFactory;

import java.util.Comparator;

public class ComparatorInteger implements Comparator<Object> {
    public int compare(Object val1, Object val2) {
        return  Integer.compare(Integer.parseInt((String)val1), Integer.parseInt((String)val2));
    }
}
