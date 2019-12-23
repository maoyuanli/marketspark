package tweets;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.*;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TweetSparker {
    SparkSession sparkSession = SparkSession.builder().appName("Tweet2Spark")
            .master("local")
            .getOrCreate();

    StructField[] tweetSchemaFields = new StructField[]{
            new StructField("created_at", DataTypes.StringType, true, Metadata.empty()),
            new StructField("user", DataTypes.StringType, true, Metadata.empty()),
            new StructField("text", DataTypes.StringType, true, Metadata.empty())
    };

    StructType tweetSchema = new StructType(tweetSchemaFields);

    TweetFetcher tweetFetcher = new TweetFetcher(new ArrayList<String>(Arrays.asList("realdonaldTrump")));
    String trumpTweetsStr = tweetFetcher.tweetsList();

    public void sparkup(){
        Gson gson = new Gson();
        JsonObject trumpTweetsJson = gson.fromJson(trumpTweetsStr, JsonObject.class);
        JsonArray tweets= (JsonArray) trumpTweetsJson.get("tweets");
        List<String> tweetsList = gson.fromJson(tweets.toString(), List.class);
        System.out.println(tweetsList.toString());
    }

}
