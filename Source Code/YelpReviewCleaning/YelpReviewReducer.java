import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class YelpReviewReducer extends Reducer<Text, Text, NullWritable, Text> {

    public void reduce(Text key, Iterable<Text> values, Context context)
    throws IOException, InterruptedException {

      String cleanedOutput = "";
        for (Text value : values) {
            String valueString = value.toString();
            if (valueString != null && !valueString.isEmpty()) {
                if (cleanedOutput.isEmpty()) {
                    cleanedOutput = valueString;
                } else {
                    cleanedOutput += "," + valueString;
                }
            }
        }

        if (!cleanedOutput.isEmpty()) {
            context.write(NullWritable.get(), new Text(cleanedOutput));
        }

    }

}