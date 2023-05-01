import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.rules.ZeroR;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import weka.core.converters.CSVLoader;
public class ZeroRModel {

  public static void main(String[] args) throws Exception {
    // Load CSV file
    CSVLoader loader = new CSVLoader();
    loader.setSource(new File("data/stroke_preprocessed_data.csv"));
    Instances data_csv = loader.getDataSet();

    // Save ARFF file
    ArffSaver saver = new ArffSaver();
    saver.setInstances(data_csv);
    saver.setFile(new File("data/stroke_preprocessed_data.arff"));
    saver.writeBatch();

    // Import data.arff
    BufferedReader reader = new BufferedReader(new FileReader("data/stroke_preprocessed_data.arff"));
    Instances data_arff = new Instances(reader);
    reader.close();
    data_arff.setClassIndex(data_arff.numAttributes() - 1);

    // Train ZeroR model
    ZeroR model = new ZeroR();
    model.buildClassifier(data_arff);

    // Evaluate model
    Evaluation eval = new Evaluation(data_arff);
    eval.evaluateModel(model, data_arff);
    System.out.println(eval.toSummaryString());

    // Make predictions
    Random rand = new Random(1000);
    int fold = 0;
    eval.crossValidateModel(model, data_arff, 10, rand);
    System.out.println(eval.toSummaryString());
  }
}