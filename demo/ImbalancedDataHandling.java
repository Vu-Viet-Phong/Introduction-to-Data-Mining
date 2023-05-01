import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.Resample;
import weka.filters.unsupervised.instance.SMOTE;
import weka.filters.unsupervised.instance.SMOTEENN;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import java.util.Random;

public class ImbalancedDataHandling {
    public static void main(String[] args) throws Exception {
        // Load dataset
        DataSource source = new DataSource("data/healthcare-dataset-stroke-data.arff");
        Instances data = source.getDataSet();

        // Remove unwanted attributes (if any)
        Remove removeFilter = new Remove();
        removeFilter.setAttributeIndices("1,4");
        removeFilter.setInputFormat(data);
        Instances filteredData = Filter.useFilter(data, removeFilter);

        // Apply SMOTEENN method to balance data
        SMOTEENN smoteennFilter = new SMOTEENN();
        smoteennFilter.setInputFormat(filteredData);
        Instances balancedData = Filter.useFilter(filteredData, smoteennFilter);

        // Split data into train and test sets
        balancedData.randomize(new Random());
        int trainSize = (int) Math.round(balancedData.numInstances() * 0.8);
        int testSize = balancedData.numInstances() - trainSize;
        Instances trainData = new Instances(balancedData, 0, trainSize);
        Instances testData = new Instances(balancedData, trainSize, testSize);

        // Build and evaluate classifier
        J48 tree = new J48();
        FilteredClassifier fc = new FilteredClassifier();
        fc.setClassifier(tree);
        fc.buildClassifier(trainData);
        Evaluation eval = new Evaluation(trainData);
        eval.evaluateModel(fc, testData);
        System.out.println(eval.toSummaryString());
    }
}
