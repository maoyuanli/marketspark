package tweets;

import com.maotion.tweets.TweetElement;
import com.maotion.tweets.TweetFetcher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class TweetFetcherTest {

    @Test
    void twitterInstance() {
    }

    @Test
    void tweetsList() {
        TweetFetcher tweetFetcher = new TweetFetcher(new ArrayList<String>( Arrays.asList("realdonaldTrump") ));
        ArrayList<TweetElement> trumpTweets = tweetFetcher.tweetsList();
        System.out.println(trumpTweets);
    }

    @Test
    void tweetQueryBuilder() {
    }
}