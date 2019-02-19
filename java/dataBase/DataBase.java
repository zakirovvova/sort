package dataBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class DataBase implements Iterator {
    private ArrayList<ReadUnit> list;
    private Comparator<Object> comparator;
    private int sortMode;
    private boolean isNext;
    public DataBase(ArrayList<ReadUnit> list, Comparator<Object> comparator, int sortMode) {
        this.list = list;
        //удаляем пустые единицы с пустыми файлами
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).hasNext()) {
                list.remove(i);
                i = -1;
            }
        }
        if (0 == list.size()) {
            isNext = false;
        } else {
            isNext = true;
        }
        this.comparator = comparator;
        this.sortMode = sortMode;
    }

    public boolean hasNext() {
        return isNext;
    }
    public String next() {
        return getMinElement();
    }
    private String getMinElement() {
        int index = 0;
        ReadUnit unit = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (0 < (sortMode * comparator.compare(unit.getElement(), list.get(i).getElement()))) {
                index = i;
                unit = list.get(i);
            }
        }
        String string = (String)unit.next();
        if (!unit.hasNext()) {
            list.remove(index);
            if (0 == list.size()) {
                isNext = false;
            }
        }
        return string;
    }
}