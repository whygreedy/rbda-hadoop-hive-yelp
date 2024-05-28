import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class YelpReviewMapper extends Mapper<LongWritable, Text, Text, Text> {

    private Text cleaned_data = new Text();

    public void map(LongWritable key, Text value, Context context)
    throws IOException, InterruptedException {

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(value.toString());

            String review_id = (String) json.get("review_id");
            String user_id = (String) json.get("user_id");
            String business_id = (String) json.get("business_id");
            double stars = (double) json.get("stars");


            // join value with commas
            String cleanedString = String.join(",", review_id, user_id, business_id, String.valueOf(stars));
            cleaned_data.set(cleanedString);

            context.write(new Text(review_id), cleaned_data);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
