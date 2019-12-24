import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import tweets.TweetSparker;

import java.util.ArrayList;
import java.util.Arrays;

public class MarketSparkApp {
    public static void main(String[] args) {
        TweetSparker trumpSparker = new TweetSparker(new ArrayList<>(Arrays.asList("realDonaldTrump")));
        Dataset<Row> trumpTweetset = trumpSparker.generateDF();
        trumpTweetset.show();
        trumpTweetset.printSchema();
    }
}
