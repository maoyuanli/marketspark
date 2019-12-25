package tweets;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TweetSparkerTest {

    @Test
    void generateDF() throws IOException {
        SparkSession sparkSession = SparkSession.builder().master("local").getOrCreate();
        TweetSparker tweetSparker = new TweetSparker(new ArrayList<>(Arrays.asList("realDonaldTrump")),sparkSession);
        Dataset<Row> df = tweetSparker.generateDF(true);
        df.show();
        df.printSchema();
    }
}