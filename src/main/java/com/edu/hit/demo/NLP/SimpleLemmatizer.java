package com.edu.hit.demo.NLP;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SimpleLemmatizer {
    private Map<String, String> lemmaDict;

    public SimpleLemmatizer(InputStream dictFile) throws Exception {
        lemmaDict = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(dictFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\t");
            if (parts.length == 2) {
                lemmaDict.put(parts[0], parts[1]);
            }
        }
    }

    public String lemmatize(String word, String posTag) {
        // Here we assume the dictionary contains the word in its base form
        // Modify this logic if your dictionary format is different
        String lemma = lemmaDict.get(word + "_" + posTag);
        return lemma != null ? lemma : word;
    }
}
