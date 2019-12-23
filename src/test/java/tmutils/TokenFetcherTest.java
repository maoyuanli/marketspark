package tmutils;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TokenFetcherTest {

    @org.junit.jupiter.api.Test
    void fetchToken() {
        TokenFetcher tokenFetcher = new TokenFetcher();
        HashMap actual = tokenFetcher.fetchToken("token.json");
        System.out.println(actual);
    }
}