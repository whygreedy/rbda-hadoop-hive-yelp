import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.hadoop.io.IntWritable;

public class DemographicMapper extends Mapper<LongWritable, Text, Text, Text> {

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(value.toString());

            String jurisdiction_name = (String) json.get("jurisdiction_name");
            long count_participants = (long) json.get("count_participants");
            double percent_female = (double) json.get("percent_female");
            double percent_male = (double) json.get("percent_male");
            double percent_gender_unknown = (double) json.get("percent_gender_unknown");
            double percent_pacific_islander = (double) json.get("percent_pacific_islander");
            double percent_hispanic_latino = (double) json.get("percent_hispanic_latino");
            double percent_american_indian = (double) json.get("percent_american_indian");
            double percent_asian_non_hispanic = (double) json.get("percent_asian_non_hispanic");
            double percent_white_non_hispanic = (double) json.get("percent_white_non_hispanic");
            double percent_black_non_hispanic = (double) json.get("percent_black_non_hispanic");
            double percent_other_ethnicity = (double) json.get("percent_other_ethnicity");
            double percent_permanent_resident_alien = (double) json.get("percent_permanent_resident_alien");
            double percent_us_citizen = (double) json.get("percent_us_citizen");
            double percent_other_citizen_status = (double) json.get("percent_other_citizen_status");
            double percent_receives_public_assistance = (double) json.get("percent_receives_public_assistance");
            double percent_nreceives_public_assistance = (double) json.get("percent_nreceives_public_assistance");
            if (count_participants !=0) {           
                context.write(new Text(jurisdiction_name), new Text(count_participants + "\t" + percent_female + "\t" + percent_male + "\t" + percent_gender_unknown + "\t" + percent_pacific_islander + "\t" + percent_hispanic_latino + "\t" + percent_american_indian + "\t" + percent_asian_non_hispanic + "\t" + percent_white_non_hispanic + "\t" + percent_black_non_hispanic + "\t" + percent_other_ethnicity + "\t" + percent_permanent_resident_alien + "\t" + percent_us_citizen + "\t" + percent_other_citizen_status + "\t" + percent_receives_public_assistance + "\t" + percent_nreceives_public_assistance));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
