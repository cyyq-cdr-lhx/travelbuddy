package com.edu.hit.demo.NLP;



import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NLPUtil {

    private POSTaggerME posTagger;
    private SimpleLemmatizer lemmatizer;

    public NLPUtil() {
        try {
            InputStream posModelIn = getClass().getResourceAsStream("/models/en-pos-maxent.bin");
            POSModel posModel = new POSModel(posModelIn);
            this.posTagger = new POSTaggerME(posModel);

            InputStream dictLemmatizer = getClass().getResourceAsStream("/models/en-lemmatizer.dict");
            this.lemmatizer = new SimpleLemmatizer(dictLemmatizer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> extractKeywords(String text) {
        String[] tokens = WhitespaceTokenizer.INSTANCE.tokenize(text);
        String[] posTags = posTagger.tag(tokens);

        List<String> keywords = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
            String lemma = lemmatizer.lemmatize(tokens[i], posTags[i]);
            if (isRelevantPOS(posTags[i])) {
                keywords.add(lemma);
            }
        }
        return keywords;
    }

    private boolean isRelevantPOS(String posTag) {
        return posTag.startsWith("NN") || posTag.startsWith("VB") || posTag.startsWith("JJ");
    }
}
