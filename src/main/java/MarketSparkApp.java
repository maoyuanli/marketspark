import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import tmutils.SentimentAnalyzer;
import tweets.TweetSparker;

import java.util.ArrayList;
import java.util.Arrays;

import static org.apache.spark.sql.functions.col;

public class MarketSparkApp {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().master("local").getOrCreate();
        TweetSparker trumpSparker = new TweetSparker(new ArrayList<>(Arrays.asList("realDonaldTrump")),sparkSession);
        Dataset<Row> trumpTweetset = trumpSparker.generateDF();
        TweetSparker mediaSparker = new TweetSparker(new ArrayList<>(Arrays.asList("marketwatch", "wsj", "ft", "business", "theeconomist", "cnbc", "cnn")),sparkSession);
        Dataset<Row> mediaTweetset = mediaSparker.generateDF();
        trumpTweetset.createOrReplaceTempView("trumptweets");
        mediaTweetset.createOrReplaceTempView("mediatweets");
        Dataset<Row> trumpByDate = sparkSession.sql("SELECT date, concat_ws(';',collect_list(text)) as combinedTrumpTweets FROM trumptweets GROUP BY date");
        Dataset<Row> mediaByDate = sparkSession.sql("SELECT date, concat_ws(';',collect_list(text)) as combinedMediaTweets FROM mediatweets GROUP BY date");
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        UserDefinedFunction getSentiment = sentimentAnalyzer.getSentimentUDF(false);
        trumpByDate.show();
        trumpByDate.printSchema();
        mediaByDate.show();
        mediaByDate.printSchema();
    }
}
