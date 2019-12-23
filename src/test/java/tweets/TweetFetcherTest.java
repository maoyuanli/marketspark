package tweets;

import com.google.gson.JsonArray;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TweetFetcherTest {

    @Test
    void twitterInstance() {
    }

    @Test
    void tweetsList() {
        TweetFetcher tweetFetcher = new TweetFetcher(new ArrayList<String>( Arrays.asList("realdonaldTrump") ));
        JsonArray trumpTweets = tweetFetcher.tweetsList();
        System.out.println(trumpTweets);
        System.out.println(trumpTweets.size());
    }

    @Test
    void tweetQueryBuilder() {
    }
}