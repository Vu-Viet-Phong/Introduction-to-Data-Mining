import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.util.Precision;
import org.apache.commons.math3.util.ResizableDoubleArray;
import org.apache.commons.math3.util.ResizableDoubleArray;

import java.util.Arrays;
import java.util.List;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.selection.Selection;
import tech.tablesaw.table.TableSliceGroup;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.instance.RemoveWithValues;

public class DataPreparation {
  public static void main(String[] args) throws Exception {
    // Load the dataset
    Table stroke = Table.read().csv("data/healthcare-dataset-stroke-data.csv");

    // Replace gender values
    stroke.replaceString("gender", "Male", "0");
    stroke.replaceString("gender", "Female", "1");

    // Replace smoking_status values
    Map<String, String> replacementDictionary = Stream.of(new Object[][] {
      { "never smoked", "0" }, { "Unknown", "1" },
      { "formerly smoked", "2" }, { "smokes", "3" }
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (String) data[1]));

    stroke.replaceString("smoking_status", replacementDictionary);

    // Split dataset into features (X) and target variable (y)
    Table X = stroke.dropColumns("stroke");
    DoubleColumn y = stroke.doubleColumn("stroke");

    // Split the data into training and testing sets
    Table[] trainTest = X.sampleSplit(0.7);
    Table X_train = trainTest[0];
    Table X_test = trainTest[1];
    DoubleColumn y_train = y.where(trainTest[0].getRowCount(), Selection.ALL);
    DoubleColumn y_test = y.where(trainTest[1].getRowCount(), Selection.ALL);

    // Define the data preprocessing pipeline
    StringColumn catColumns = (StringColumn) X_train.columnsOfType(ColumnType.STRING).get(0);
    DoubleColumn numColumns = (DoubleColumn) X_train.columnsOfType(ColumnType.DOUBLE).get(0);

    // Define the preprocessing steps for categorical columns
    RemoveWithValues rm = new RemoveWithValues();
    rm.setAttributeIndex(Integer.toString(catColumns.index()));
    rm.setInvertSelection(true);
    rm.setMatchMissingValues(true);

    StringToNominal stn = new StringToNominal();
    stn.setAttributeRange(Integer.toString(catColumns.index() + 1));

    ReplaceMissingValues rmv = new ReplaceMissingValues();
    rmv.setAttributeIndices(Integer.toString(catColumns.index() + 1));

    Filter[] catFilters = { rm, stn, rmv };

    // Define the preprocessing steps for numerical columns
    Remove rmNum = new Remove();
    rmNum.setAttributeIndices(Integer.toString(numColumns.index()));

    ReplaceMissingValues rmvNum = new ReplaceMissingValues();
}
