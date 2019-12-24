package tweets;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.Arrays;


public class TweetSparker {
    SparkSession sparkSession = SparkSession.builder().appName("Tweet2Spark")
            .master("local")
            .getOrCreate();

    StructField[] tweetSchemaFields = new StructField[]{
            new StructField("tweets", DataTypes.StringType, true, Metadata.empty()),
            new StructField("user", DataTypes.StringType, true, Metadata.empty()),
            new StructField("text", DataTypes.StringType, true, Metadata.empty())
    };

    StructType tweetSchema = DataTypes.createStructType(tweetSchemaFields);

    TweetFetcher tweetFetcher = new TweetFetcher(new ArrayList<String>(Arrays.asList("realdonaldTrump")));
    ArrayList<TweetElement> trumpTweets = tweetFetcher.tweetsList();


    public void sparkup() {
        Dataset<Row> df = sparkSession.createDataFrame(trumpTweets, TweetElement.class);
        df.show();
    }

}
