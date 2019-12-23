package tweets;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TweetSparkerTest {

    @Test
    void sparkup() throws IOException {
        TweetSparker tweetSparker = new TweetSparker();
        tweetSparker.sparkup();
    }
}