import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import pandas.DataFrame;

public class StrokeInfo {

    public static void main(String[] args) {
        // Load the stroke dataset from a CSV file
        DataFrame strokeData = loadCsv("data/healthcare-dataset-stroke-data.csv");

        // Print the summary statistics of the DataFrame
        System.out.println(strokeData.describe());
    }

    private static DataFrame loadCsv(String filename) {
        try {
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.exec("import pandas as pd");
            interpreter.exec("df = pd.read_csv('" + filename + "')");
            PyObject df = interpreter.get("df");
            return new DataFrame(df);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
