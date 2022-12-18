package model.database.utilities;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.database.utilities.ExcelPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public abstract class ExcelLoadSaveTemplate<K, V> {

    public TreeMap<K, V> load(File file) {
        TreeMap<K, V> returnMap = new TreeMap<>();
        ExcelPlugin excelPlugin = new ExcelPlugin();
        try {
            ArrayList<ArrayList<String>> rowInfo = excelPlugin.read(file);
            for (ArrayList<String> row : rowInfo) {
                String[] tokens = row.toArray(new String[0]);
                V element = maakObject(tokens);
                K key = getKey(tokens);
                returnMap.put(key, element);
            }
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
        return returnMap;
    }

    public void save(Map<K, V> map, File file) {
        ExcelPlugin excelPlugin = new ExcelPlugin();
        ArrayList<ArrayList<String>> array = new ArrayList<>();
        try {
            map.forEach((key, value) -> {
                ArrayList<String> arrayString = new ArrayList<>();
                arrayString.add(String.valueOf(key));
                arrayString.addAll(Arrays.asList(value.toString().split(";")));
                array.add(arrayString);
            });
            excelPlugin.write(file, array);
        } catch (IOException | BiffException | WriteException e) {
            e.printStackTrace();
        }
    }

    protected abstract V maakObject(String[] tokens);
    protected abstract K getKey(String[] tokens);
}