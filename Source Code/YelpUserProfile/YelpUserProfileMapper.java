import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class YelpUserProfileMapper extends Mapper<LongWritable, Text, Text, MinMaxCountTuple> {

    private final JSONParser parser = new JSONParser();

    public void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {

        try {
            JSONObject json = (JSONObject) parser.parse(value.toString());

            String user_id = (String) json.get("user_id");
            long review_count = (long) json.get("review_count");

            MinMaxCountTuple tuple = new MinMaxCountTuple(review_count, review_count, 1);
            context.write(new Text(user_id), tuple);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
