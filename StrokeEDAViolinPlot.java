import com.twosigma.beakerx.chart.xychart.Plot;
import com.twosigma.beakerx.chart.categoryplot.CategoryPlot;
import com.twosigma.beakerx.chart.categoryplot.CategoryPlotChart;
import com.twosigma.beakerx.chart.categoryplot.plotitem.CategoryBars;
import com.twosigma.beakerx.chart.categoryplot.plotitem.CategoryLines;
import com.twosigma.beakerx.chart.categoryplot.plotitem.CategoryPoints;
import com.twosigma.beakerx.chart.categoryplot.plotitem.CategoryStems;
import com.twosigma.beakerx.chart.categoryplot.plotitem.CategoryStems.StemOrientation;
import com.twosigma.beakerx.chart.categoryplot.plotitem.CategoryViolin;
import com.twosigma.beakerx.chart.categoryplot.plotitem.CategoryViolin.FillType;
import com.twosigma.beakerx.table.format.TableDisplayStringFormat;
import com.twosigma.beakerx.table.highlight.TableDisplayCellHighlighter;
import com.twosigma.beakerx.table.highlight.TableDisplayCellHighlighter.HighlightStyle;
import com.twosigma.beakerx.table.highlight.TableDisplayCellHighlighter.ValueMatcher;
import com.twosigma.beakerx.table.highlight.ThreeColorHeatmapHighlighter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class StrokeEDAViolinPlot {

    public static void main(String[] args) {
        // Load the stroke dataset from a CSV file
        List<Map<String, String>> strokeData = loadCsv("data/healthcare-dataset-stroke-data.csv");

        // Create a violin plot of age vs. heart disease status
        CategoryViolin violin = new CategoryViolin();
        violin.setX(Arrays.asList("0", "1"));
        violin.setY(strokeData.stream().map(row -> Double.parseDouble(row.get("age"))).collect(Collectors.toList()));
        violin.setFillType(FillType.tozero);
        violin.setBoxVisible(true);

        CategoryPlot plot = new CategoryPlot();
        plot.add(violin);
        plot.setTitle("Age vs. Heart Disease Status");
        plot.setXLabel("Heart Disease");
        plot.setYLabel("Age");

        CategoryPlotChart chart = new CategoryPlotChart(plot);
        chart.display();
    }

    private static List<Map<String, String>> loadCsv(String filename) {
        try {
            return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(Files.newBufferedReader(Paths.get(filename)))
                    .getRecords().stream().map(record -> {
                        Map<String, String> row = record.toMap();
                        return row;
                    }).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
