package idm;

import java.util.Random;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.rules.ZeroR;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ZerorAlgorithm {
	public void ZeroR(int fold) throws Exception {
		// Load dataset
		DataSource source = new DataSource("C:\\Users\\Admin\\Desktop\\MyDoc\\my-workspace\\idm-project\\src\\idm\\data\\stroke-data.arff");
	    Instances data_arff = source.getDataSet();
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
	    eval.crossValidateModel(model, data_arff, fold, rand);
	    System.out.println(eval.toSummaryString());
	}
	
	public static void main(String[] args) throws Exception {
		ZerorAlgorithm pp = new ZerorAlgorithm();
		
		pp.ZeroR(0);
		System.out.println("################### 10-fold cross-validation ###################");
		pp.ZeroR(10);
	}
}
