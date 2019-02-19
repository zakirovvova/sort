package Manager;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

import errorHandler.*;
import factory.checkerFactory.GenerateChecker;
import factory.checkerFactory.InputChecker;
import factory.comparatorFactory.GenerateComparator;
import dataBase.*;

public class Manager {
    public void run(String[] massStr) {

        if (0 == massStr.length) {
            ErrorHandler.noArguments();
            return;
        }
        InputChecker checker = null;
        try {
            //проверка на корректность введенных данных
            checker = getChecker(massStr);
        } catch (IOException e) {
            //случится, если отсутствуют ключи -s или -i
            return;
        }
        Comparator<Object> comparator = getComparator(massStr);

        int sortMode = getSortMode(massStr);

        FileWriter writer = null;
        try {
            writer = GenerateOutputFile(massStr);
        } catch (IOException e) {
            //случится, если не передано имя выходного файла
            return;
        }

        ArrayList<ReadUnit> list = null;
        try {
            list = createData(massStr, checker, comparator, sortMode);
        } catch (IOException e) {
            try {
                if (null != writer) {
                    writer.close();
                }
            } catch (IOException etoo) {
                etoo.printStackTrace();
            }
            return;
        }

        //возвращает новый минимальный или максимальный элемент
        Iterator dataBase = new DataBase(list, comparator, sortMode);

        FillerOutputFile fillerOutputFile = new FillerOutputFile(dataBase, writer);//
        //записывает в файл
        fillerOutputFile.create();
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private InputChecker getChecker(String[] massStr) throws IOException {
        InputChecker checker = null;
        for (int i = 0; i < massStr.length; i++) {
            if ('-' == massStr[i].charAt(0)) {
                if (GenerateChecker.isKeyInDatabase(massStr[i])) {
                    if (null == checker) {
                        checker = GenerateChecker.getChecker(massStr[i]);
                    } else {
                        ErrorHandler.keyDataOverride();
                    }
                }
            }
        }
        if (null == checker) {
            ErrorHandler.noDataKeys();
            throw new IOException();
        }
        return checker;
    }

    private Comparator<Object> getComparator(String[] massStr) {
        Comparator<Object> comparator = null;
        for (int i = 0; i < massStr.length; i++) {
            if ('-' == massStr[i].charAt(0)) {
                if (GenerateComparator.isKeyInDatabase(massStr[i])) {
                    if (null == comparator) {
                        comparator = GenerateComparator.getComparator(massStr[i]);
                    } else {
                        ErrorHandler.keyDataOverride();
                    }
                    massStr[i] = null;
                }
            }
        }
        return comparator;
    }

    private int getSortMode(String[] massStr) {
        int sortMode = 0;
        for (int i = 0; i < massStr.length; i++) {
            if (null != massStr[i] && '-' == massStr[i].charAt(0)) {
                switch (massStr[i]) {
                    case "-a" :
                        if (0 == sortMode) {
                            sortMode = 1;
                        } else {
                            ErrorHandler.keySortOverride();
                        }
                        massStr[i] = null;
                        break;
                    case "-d" :
                        if (0 == sortMode) {
                            sortMode = -1;
                        } else {
                            ErrorHandler.keySortOverride();
                        }
                        massStr[i] = null;
                        break;
                }
            }
        }
        if (0 == sortMode) {
            sortMode = 1;
        }
        return  sortMode;
    }
    private  FileWriter GenerateOutputFile(String[] massStr ) throws IOException {
        FileWriter writer = null;
        for (int i = 0; i < massStr.length; i++) {
            if (null != massStr[i]) {
                try {
                    writer = new FileWriter(massStr[i], false);
                    massStr[i] = null;
                    return writer;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (null == writer) {
            ErrorHandler.noOutputFileName();
            throw new IOException();
        }
        return null;
    }

    private ArrayList<ReadUnit> createData(String[] massSrt, InputChecker checker,
                                           Comparator<Object> comparator, int sortMode) throws IOException {
        boolean check = false;
        for (String str : massSrt) {
            if (null != str) {
                check = true;
                break;
            }
        }
        if (!check) {
            ErrorHandler.noFileForReading();
            throw new IOException();
        }

        Scanner scanner = null;
        ArrayList<ReadUnit> list = new ArrayList<>();
        for (int i = 0; i < massSrt.length; i++) {
            if (null != massSrt[i]) {
                File file = new File(massSrt[i]);
                if (!file.exists()) {
                    ErrorHandler.missingFile(massSrt[i]);
                    continue;
                } else {
                    scanner = new Scanner(new FileReader(file));
                    list.add(new ReadUnit(scanner, checker, comparator, sortMode));
                }
            }
        }
        if (0 == list.size()) {
            ErrorHandler.noFilesToRead();
            throw new IOException();
        }
        return list;
    }
}