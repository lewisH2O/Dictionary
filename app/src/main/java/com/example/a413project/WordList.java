package com.example.a413project;

import java.util.ArrayList;
import java.util.List;

public class WordList {

    List<Word> wordList = new ArrayList<>();

    public void addWord(String word, String phonetic, String audio, String speech, String definition, String synonyms, String antonyms, String example){
        Word target = new Word(word, phonetic, audio, speech, definition, synonyms, antonyms, example);
        wordList.add(target);
    }

    public Word[] getItems() {
        return wordList.toArray(new Word[wordList.size()]);
    }

    public void clear(){
        wordList.clear();
    }

}
