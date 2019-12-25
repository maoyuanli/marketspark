package com.maotion;

import com.maotion.tweets.TweetSparker;
import com.maotion.utils.SentimentAnalyzer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedFunction;

import java.util.ArrayList;
import java.util.Arrays;

import static org.apache.spark.sql.functions.*;
import static org.apache.spark.sql.functions.col;

public class App {
    public static void main(String[] args) {

        SparkSession sparkSession = SparkSession.builder().master("local").getOrCreate();
        sparkSession.sparkContext().setLogLevel("ERROR");
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        UserDefinedFunction getSentiment = sentimentAnalyzer.getSentimentUDF(false);

        // Trump
        TweetSparker trumpSparker = new TweetSparker(new ArrayList<>(Arrays.asList("realDonaldTrump")), sparkSession);
        Dataset<Row> trumpTweetset = trumpSparker.generateDF(false);
        Dataset<Row> trumpByDate = trumpTweetset.groupBy("date").agg(concat_ws(" ", collect_list("text")).alias("trumpTweets"));
        Dataset<Row> trump = trumpByDate.withColumn("trumpSentiment", getSentiment.apply(col("trumpTweets"))).sort(col("date").desc());
        // Media
        TweetSparker mediaSparker = new TweetSparker(new ArrayList<>(Arrays.asList("wsj", "ft", "cnn")), sparkSession);
        Dataset<Row> mediaTweetset = mediaSparker.generateDF(false);
        Dataset<Row> mediaByDate = mediaTweetset.groupBy("date").agg(concat_ws(" ", collect_list("text")).alias("mediaTweets"));
        Dataset<Row> media = mediaByDate.withColumn("mediaSentiment", getSentiment.apply(col("mediaTweets"))).sort(col("date").desc());

        trump.show();
        media.show();
    }
}
