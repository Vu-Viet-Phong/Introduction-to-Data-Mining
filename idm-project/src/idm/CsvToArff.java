package idm;

import java.io.File;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class CsvToArff {
	public static void main(String[] args) throws Exception {
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File("C:\\Users\\Admin\\Desktop\\MyDoc\\my-workspace\\idm-project\\src\\idm\\data\\stroke.csv"));
		Instances data = loader.getDataSet();
		
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File("C:\\Users\\Admin\\Desktop\\MyDoc\\my-workspace\\idm-project\\src\\idm\\data\\stroke-data.arff"));
		saver.writeBatch();
	}
}
