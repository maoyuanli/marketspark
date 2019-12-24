package tweets;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


import java.util.ArrayList;

public class TweetSparker {

    private ArrayList<String> twitterAccounts;
    private SparkSession sparkSession = SparkSession.builder().master("local").getOrCreate();

    public TweetSparker(ArrayList<String> twitterAccounts) {
        this.twitterAccounts = twitterAccounts;
    }


    public Dataset<Row> generateDF() {
        TweetFetcher tweetFetcher = new TweetFetcher(this.twitterAccounts);
        ArrayList<TweetElement> trumpTweets = tweetFetcher.tweetsList();
        Dataset<Row> df = this.sparkSession.createDataFrame(trumpTweets, TweetElement.class);
        return df;
    }

}
