package tmutils;

import com.maotion.utils.TokenFetcher;

import java.util.HashMap;

class TokenFetcherTest {

    @org.junit.jupiter.api.Test
    void fetchToken() {
        TokenFetcher tokenFetcher = new TokenFetcher();
        HashMap actual = tokenFetcher.fetchToken("token.json");
        System.out.println(actual);
    }
}