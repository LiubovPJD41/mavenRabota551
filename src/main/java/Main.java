import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";

        List<Employee> list = parseCSV(columnMapping, fileName);
        if (list == null) {
            System.out.println("The list is empty!");
        }
        for (Employee employee : list) {
            System.out.println(employee);
        }

        String json = listToJson(list);

        writeString(json,"data.json");
    }
    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {  //Чтение Java класса из CSV
        try (CSVReader reader = new CSVReader(new FileReader("data.csv"))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping("id", "firstName", "lastName", "country", "age");

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader) //???
                    .withMappingStrategy(strategy)
                    .build();
            return csv.parse();
            //List<Employee> list = csv.parse();
            //list.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String listToJson(List<Employee> list) {  //список преобразуйте в строчку в формате JSON
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        System.out.println(gson.toJson(list));
        return gson.toJson(list);
    }
    public static void writeString(String json, String fileName) {   //
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




