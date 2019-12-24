package tmutils;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;


import java.util.Properties;

public class SentimentAnalyzer {

    static StanfordCoreNLP pipeline;


    public static void init() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }
    public int getSentment(String content) {
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
            return mainSentiment;
//        switch (mainSentiment) {
//            case 0:
//                return "Very Negative";
//            case 1:
//                return "Negative";
//            case 2:
//                return "Neutral";
//            case 3:
//                return "Positive";
//            case 4:
//                return "Very Positive";
//            default:
//                return "";
//        }
        }
    }

