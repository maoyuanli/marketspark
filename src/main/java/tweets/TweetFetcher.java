package tweets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tmutils.TokenFetcher;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TweetFetcher {
    private ArrayList<String> twitterAccounts;

    public TweetFetcher(ArrayList<String> twitterAccounts) {
        this.twitterAccounts = twitterAccounts;
    }

    public static Twitter twitterInstance() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        TokenFetcher tokenFetcher = new TokenFetcher();
        HashMap<String, String> twitterTokens = tokenFetcher.fetchToken("token.json");

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(twitterTokens.get("api_key"))
                .setOAuthConsumerSecret(twitterTokens.get("api_secret"))
                .setOAuthAccessToken(twitterTokens.get("access_token"))
                .setOAuthAccessTokenSecret(twitterTokens.get("access_secret"));
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
    }

    public String tweetsList() {
        String queryStr = tweetQueryBuilder(twitterAccounts);
        Twitter twitter = twitterInstance();
        Query query = new Query();
        query.count(100).setLang("en");
        query.setQuery(queryStr);
        String tweetRsltStr = null;
        try {
            QueryResult result = twitter.search(query);
            List<Status> statuses = result.getTweets();
            Gson gson = new Gson();
            String tweets = gson.toJson(statuses);
            JsonArray tweetsJson = (JsonArray) new JsonParser().parse(tweets);
            JsonObject tweetWrapped = new JsonObject();
            tweetWrapped.add("tweets", tweetsJson);
            tweetRsltStr = tweetWrapped.toString();
        } catch (TwitterException e) {
            //ToDo
        }
        return tweetRsltStr;
    }

    public static String tweetQueryBuilder(ArrayList<String> twitterAccounts) {

        String fromPrefix = "FROM:";
        String orPrefix = " OR ";
        StringBuilder query = new StringBuilder();
        ArrayList<String> sources = twitterAccounts;
        for (int i = 0; i < sources.size(); i++) {
            if (!(i == sources.size() - 1)) {
                query.append(fromPrefix).append(sources.get(i)).append(orPrefix);
            } else {
                query.append(fromPrefix).append(sources.get(i));
            }
        }

        return query.toString();
    }
}
