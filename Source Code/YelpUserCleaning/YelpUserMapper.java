import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class YelpUserMapper extends Mapper<LongWritable, Text, Text, Text> {

    private Text cleaned_data = new Text();

    public void map(LongWritable key, Text value, Context context)
    throws IOException, InterruptedException {

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(value.toString());

            String user_id = (String) json.get("user_id");
            String name = ((String) json.get("name")).replace(",", ""); // replace commas with space to make data seperation correctly in Hive
            long review_count = (long) json.get("review_count");
            String yelping_since = (String) json.get("yelping_since");
            double average_stars = (double) json.get("average_stars");
            String friends = (String) json.get("friends");
            long useful = (long) json.get("useful");
            long funny = (long) json.get("funny");
            long cool = (long) json.get("cool");

            // retrieve year
            String join_year = "";
            if (yelping_since != null) {
                join_year = yelping_since.substring(0, 4);
            }

            // count the number of friends and identify None value and store it as 0 friends_count
            String[] friendsList = friends.split(",");
            long friends_count = 0;
            if (friendsList.length == 1 && friendsList[0].equals("None")) {
                friends_count = 0;
            } else {
                friends_count = friendsList.length;
            }


            // join value with commas
            String cleanedString = String.join(",", user_id, name, String.valueOf(review_count), join_year, String.valueOf(average_stars), String.valueOf(friends_count), String.valueOf(useful), String.valueOf(funny), String.valueOf(cool));
            cleaned_data.set(cleanedString);

            context.write(new Text(user_id), cleaned_data);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
