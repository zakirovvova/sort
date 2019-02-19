package dataBase;

import errorHandler.ErrorHandler;
import factory.checkerFactory.InputChecker;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class ReadUnit implements Iterator<Object> {
    private  Scanner scanner;
    private InputChecker checker;
    private Comparator<Object> comparator;
    private StringBuilder str1 = new StringBuilder();
    private StringBuilder str2 = new StringBuilder();
    private final int sortMode;
    private boolean ifNext;
    public ReadUnit(Scanner scanner, InputChecker checker, Comparator<Object> comparator, int sortMode) {
        this.checker = checker;
        this.scanner = scanner;
        this.comparator = comparator;
        this.sortMode = sortMode;
        ifNext = true;
        StringBuilder string = new StringBuilder();
        if (!scanner.hasNextLine()) {
            ifNext = false;
            scanner.close();
            scanner = null;
        }
        while (null != scanner && scanner.hasNextLine() && 0 == str1.length()) {
            string.delete(0, string.length());
            string.append(scanner.next());
            if (checker.isCorrect(string.toString())) {
                str1.append(string);
            }
        }
        if (0 == str1.length()) {
            ifNext = false;
        }
    }
    public boolean hasNext() {
        return this.ifNext;
    }
    public Object next() {
        String string = new String(str1);
        generateNext();
        return string;
    }
    public Object getElement() {
        return str1.toString();
    }
    private void generateNext() {
        str2.delete(0, str2.length());
        str2.append(str1);
        str1.delete(0, str1.length());
       generateNewString();
        if (0 == str1.length()) {
            ifNext = false;
            scanner.close();
        }
    }
    private void generateNewString() {//вызвать
        StringBuilder string = new StringBuilder();
        while (scanner.hasNextLine() && 0 == str1.length()) {
            string.delete(0, string.length());
            string.append(scanner.next());
            if (checker.isCorrect(string.toString())) {
                if(0 < (sortMode * comparator.compare(string.toString(), str2.toString())) ||
                    0 == (sortMode * comparator.compare(string.toString(), str2.toString()))) {
                    str1.append(string);
                }
            } else {
                ErrorHandler.incorrectData(string.toString());
            }
        }
    }
}
