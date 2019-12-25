import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import tweets.TweetSparker;

import java.util.ArrayList;
import java.util.Arrays;

public class MarketSparkApp {
    public static void main(String[] args) {
        TweetSparker trumpSparker = new TweetSparker(new ArrayList<>(Arrays.asList("realDonaldTrump")));
        Dataset<Row> trumpTweetset = trumpSparker.generateDF();
        TweetSparker mediaSparker = new TweetSparker(new ArrayList<>(Arrays.asList("marketwatch", "wsj", "ft", "business", "theeconomist", "cnbc", "cnn")));
        Dataset<Row> mediaTweetset = mediaSparker.generateDF();
        trumpTweetset.show();
        trumpTweetset.printSchema();
        mediaTweetset.show();
        mediaTweetset.printSchema();
    }
}
