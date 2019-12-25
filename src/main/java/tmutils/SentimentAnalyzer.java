package tmutils;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import edu.stanford.nlp.util.logging.RedwoodConfiguration;

import java.util.Properties;

import static org.apache.spark.sql.functions.udf;

public class SentimentAnalyzer {

    static StanfordCoreNLP pipeline;


    public static void init() {
        RedwoodConfiguration.current().clear().apply();
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public static <T> T getSentment(String content, Boolean getIntScore) {
        SentimentAnalyzer.init();
        int mainSentiment = 0;
        if (content != null && content.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(content);
            for (CoreMap sentence : annotation
                    .get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence
                        .get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }

            }
        }
        if (getIntScore == true) {
            return (T) Integer.valueOf(mainSentiment);
        } else {
            switch (mainSentiment) {
                case 0:
                    return (T) "Very Negative";
                case 1:
                    return (T) "Negative";
                case 2:
                    return (T) "Neutral";
                case 3:
                    return (T) "Positive";
                case 4:
                    return (T) "Very Positive";
                default:
                    return (T) "";
            }
        }
    }

    public UserDefinedFunction getSentimentUDF(Boolean getIntType){
        DataType sparkDataType;
        if(getIntType==true){
            sparkDataType = DataTypes.IntegerType;}
        else{
            sparkDataType = DataTypes.StringType;
        }
        UserDefinedFunction udfGetSentment = udf((String text) -> this.getSentment(text,getIntType), sparkDataType);
        return udfGetSentment;
    }
}

