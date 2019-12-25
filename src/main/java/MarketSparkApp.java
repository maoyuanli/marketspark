
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import tmutils.SentimentAnalyzer;
import tweets.TweetSparker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.spark.sql.functions.*;


public class MarketSparkApp {

    public static void main(String[] args) {
        Logger.getLogger("org.apache.spark").setLevel(Level.OFF);
        SparkSession sparkSession = SparkSession.builder().master("local").getOrCreate();
        TweetSparker trumpSparker = new TweetSparker(new ArrayList<>(Arrays.asList("realDonaldTrump")),sparkSession);
        Dataset<Row> trumpTweetset = trumpSparker.generateDF(true);
        TweetSparker mediaSparker = new TweetSparker(new ArrayList<>(Arrays.asList("marketwatch", "wsj", "ft", "business", "theeconomist", "cnbc", "cnn")),sparkSession);
        Dataset<Row> mediaTweetset = mediaSparker.generateDF(true);
        Dataset<Row> trumpByDate = trumpTweetset.groupBy(col("date")).agg(avg("sentiment").alias("trump_sentiment"));
        Dataset<Row> mediaByDate = mediaTweetset.groupBy(col("date")).agg(avg("sentiment").alias("media_sentiment"));
        trumpByDate.createOrReplaceTempView("trump");
        mediaByDate.createOrReplaceTempView("media");
        Dataset<Row> joined = sparkSession.sql("select t.*, m.media_sentiment from trump t inner join media m on t.date = m.date");
        joined.printSchema();
    }
}
