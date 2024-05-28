import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class YelpBusinessMapper extends Mapper<LongWritable, Text, Text, Text> {

    private Text cleaned_data = new Text();

    public void map(LongWritable key, Text value, Context context)
    throws IOException, InterruptedException {

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(value.toString());

            String business_id = (String) json.get("business_id");
            String name = ((String) json.get("name")).replace(",", ""); // replace commas with space to make data seperation correctly in Hive
            String city = ((String) json.get("city")).replace(",", ""); // replace commas with space to make data seperation correctly in Hive
            String state = (String) json.get("state");
            String postal_code = (String) json.get("postal_code");
            double latitude = (double) json.get("latitude");
            double longitude = (double) json.get("longitude");
            double stars = (double) json.get("stars");
            long review_count = (long) json.get("review_count");
            long is_open = (long) json.get("is_open");
            String categories = (String) json.get("categories");


            // replace commas with pipe in categories field
            String categoriesNew = "";
            if (categories != null) {
                categoriesNew = categories.replace(",", "|");
            } else {
                categoriesNew = "";
            }


            // join value with commas
            String cleanedString = String.join(",", business_id, name, city, state, postal_code, String.valueOf(latitude), String.valueOf(longitude), String.valueOf(stars), String.valueOf(review_count), String.valueOf(is_open), categoriesNew);
            cleaned_data.set(cleanedString);

            context.write(new Text(business_id), cleaned_data);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
