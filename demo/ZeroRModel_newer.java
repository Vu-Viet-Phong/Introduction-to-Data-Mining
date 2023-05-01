import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.rules.ZeroR;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Randomize;

public class ZeroRModel_newer {

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

    // Randomize the order of instances
    Randomize randomizeFilter = new Randomize();
    randomizeFilter.setInputFormat(data_arff);
    Instances randomizedData = Filter.useFilter(data_arff, randomizeFilter);
    
    // Split data into training and testing sets (70% training, 30% testing)
    int trainSize = (int) Math.round(randomizedData.numInstances() * 0.7);
    int testSize = randomizedData.numInstances() - trainSize;
    Instances trainData = new Instances(randomizedData, 0, trainSize);
    Instances testData = new Instances(randomizedData, trainSize, testSize);
    
    // Train ZeroR model
    ZeroR model = new ZeroR();
    model.buildClassifier(data_arff);

    // Evaluate model using cross-validation on entire dataset
    Evaluation eval = new Evaluation(data_arff);
    Random rand = new Random(1000);
    int fold = 10;
    eval.crossValidateModel(model, data_arff, fold, rand);
    System.out.println(eval.toSummaryString());

    // Evaluate model using train, test set
    model.buildClassifier(trainData);
    eval.evaluateModel(model, testData);
    System.out.println(eval.toSummaryString("\nResults\n======\n", false));
  }
}