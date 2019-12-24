package tweets;

import tmutils.TokenFetcher;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public ArrayList<TweetElement> tweetsList() {
        String queryStr = tweetQueryBuilder(twitterAccounts);
        Twitter twitter = twitterInstance();
        Query query = new Query();
        query.count(200).setLang("en");
        query.setQuery(queryStr);
        ArrayList<TweetElement> tweetElements = new ArrayList<>();
        try {
            QueryResult result = twitter.search(query);
            List<Status> statuses = result.getTweets();
            for(Status status:statuses){
                Date dateCreatedAt = status.getCreatedAt();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String createdAt = dateFormat.format(dateCreatedAt);
                String user = status.getUser().getName();
                String text = status.getText();
                TweetElement tweetElement = new TweetElement(user,createdAt,text);
                tweetElements.add(tweetElement);
            }

        } catch (TwitterException e) {
            //ToDo
        }
        return tweetElements;
    }

    public static String tweetQueryBuilder(ArrayList<String> twitterAccounts) {

        String fromPrefix = "FROM:";
        String orPrefix = " OR ";
        StringBuilder query = new StringBuilder();
        for (int i = 0; i < twitterAccounts.size(); i++) {
            if (!(i == twitterAccounts.size() - 1)) {
                query.append(fromPrefix).append(twitterAccounts.get(i)).append(orPrefix);
            } else {
                query.append(fromPrefix).append(twitterAccounts.get(i));
            }
        }

        return query.toString();
    }
}
