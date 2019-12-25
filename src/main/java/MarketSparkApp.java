
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import org.apache.spark.sql.types.DataTypes;
import tmutils.SentimentAnalyzer;
import tweets.TweetSparker;

import java.util.ArrayList;
import java.util.Arrays;


import static org.apache.spark.sql.functions.*;


public class MarketSparkApp {

    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder().master("local").getOrCreate();
        sparkSession.sparkContext().setLogLevel("ERROR");
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        UserDefinedFunction getSentiment = sentimentAnalyzer.getSentimentUDF(false);

        // Trump
        TweetSparker trumpSparker = new TweetSparker(new ArrayList<>(Arrays.asList("realDonaldTrump")), sparkSession);
        Dataset<Row> trumpTweetset = trumpSparker.generateDF(false);
        Dataset<Row> trumpByDate = trumpTweetset.groupBy("date").agg(concat_ws(" ", collect_list("text")).alias("textGroup"));

        // Media
        TweetSparker mediaSparker = new TweetSparker(new ArrayList<>(Arrays.asList("wsj", "ft", "cnbc", "cnn")), sparkSession);
        Dataset<Row> mediaTweetset = mediaSparker.generateDF(false);
        Dataset<Row> mediaByDate = mediaTweetset.groupBy("date").agg(concat_ws(" ", collect_list("text")).alias("textGroup"));

        Dataset<Row> trump = trumpByDate.withColumn("trumpSentiment", getSentiment.apply(col("textGroup"))).orderBy("date");
        Dataset<Row> media = mediaByDate.withColumn("mediaSentiment", getSentiment.apply(col("textGroup"))).orderBy("date");

        trump.show();
        media.show();
    }
}
