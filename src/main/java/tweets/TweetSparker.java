package tweets;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import org.apache.spark.sql.types.DataTypes;
import tmutils.SentimentAnalyzer;


import java.util.ArrayList;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.udf;

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
        Dataset<Row>dfWithDate = df.withColumn("date",col("createdAt").cast(DataTypes.DateType));
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        UserDefinedFunction getSentiment = sentimentAnalyzer.getSentimentUDF(false);
        return dfWithDate.select(col("date"),col("user"),col("text"),getSentiment.apply(col("text")).alias("sentiment"));
    }

}
