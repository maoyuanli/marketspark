package tmutils;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


public class TokenFetcher {

    public HashMap fetchToken(String tokenFile){
        URL tokenPath = Resources.getResource(tokenFile);
        try{
            String tokenContent = Resources.toString(tokenPath, Charsets.UTF_8);
            Gson gson = new Gson();
            return gson.fromJson(tokenContent,HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
