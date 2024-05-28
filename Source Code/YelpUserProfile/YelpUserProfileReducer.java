import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class YelpUserProfileReducer extends Reducer<Text, MinMaxCountTuple, Text, MinMaxCountTuple> {

    private MinMaxCountTuple result = new MinMaxCountTuple();

    public void reduce(Text key, Iterable<MinMaxCountTuple> values, Context context)
        throws IOException, InterruptedException {
        
        long minReviewCount = Long.MAX_VALUE;
        long maxReviewCount = Long.MIN_VALUE;
        long totalCount = 0;

        for (MinMaxCountTuple value : values) {
            minReviewCount = Math.min(minReviewCount, value.getMin());
            maxReviewCount = Math.max(maxReviewCount, value.getMax());
            totalCount += value.getCount();
        }

        result.setMin(minReviewCount);
        result.setMax(maxReviewCount);
        result.setCount(totalCount);

        context.write(new Text("MinReviewCount MaxReviewCount NumberOfRecords"), result);
    }
}
