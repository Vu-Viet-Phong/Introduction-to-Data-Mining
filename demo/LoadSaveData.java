import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;

public class LoadSaveData {

    public static void main(String[] args) {
      
        String csvFile = "data/healthcare-dataset-stroke-data.csv";
        CSVReader reader = null;

        try {

            reader = new CSVReader(new FileReader(csvFilePath));
            String[] line;
            while ((line = reader.readNext()) != null) {
                System.out.println(line[0] + " | " + line[1] + " | " + line[2] + " | ....");
            }
            \n        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}