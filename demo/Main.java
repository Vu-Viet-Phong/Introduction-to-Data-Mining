import org.apache.commons.math3.analysis.function.Max;
import org.apache.commons.math3.analysis.function.Min;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.apache.commons.math3.util.Pair;
import org.apache.commons.math3.util.Precision;
import org.apache.commons.math3.util.ResizableDoubleArray;
import org.apache.commons.math3.util.ResizableDoubleArrayTest;
import org.apache.commons.math3.util.ResizableDoubleArrayTest.TestDoubleArray;
import org.apache.commons.math3.util.ResizableDoubleArrayTest.TestLongArray;
import org.apache.commons.math3.util.ResizableDoubleArrayTest.TestNoData;
import org.apache.commons.math3.util.ResizableDoubleArrayTest.TestSortInvariants;
import org.apache.commons.math3.util.ResizableDoubleArrayTest.TestSubArray;
import org.apache.commons.math3.util.ResizableDoubleArrayTest.TestTrim;
import org.apache.commons.math3.util.ResizableDoubleArrayTest.TestZero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import org.apache.commons.math3.stat.descriptive.rank.Percentile.EstimationType;

import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.inference.TestUtils;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Main {

    public static void main(String[] args) throws Exception {
        DecisionTreeClassifier model = new DecisionTreeClassifier();
        Map<String, List<Integer>> param_grid = new HashMap<>();
        param_grid.put("max_depth", Arrays.asList(1, 2, 3));
        param_grid.put("min_samples_leaf", Arrays.asList(3, 15, 20));
        param_grid.put("min_samples_split", Arrays.asList(2, 10, 100));
        System.out.println(param_grid);
        GridSearchCV dt_grid_search = new GridSearchCV(model, param_grid);
        dt_grid_search.fit(X_train_under, y_train_under);
        double[] y_pred = dt_grid_search.predict(X_test_processed);
        System.out.println(Arrays.toString(y_pred));

        // test_df
    }
}
