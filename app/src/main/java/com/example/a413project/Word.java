package com.example.a413project;


public class Word {
    String word;
    String phonetic;
    String audio;
    String speech;
    String definition;
    String synonyms;
    String antonyms;
    String example;

    public Word(String word, String phonetic, String audio, String speech, String definition, String synonyms, String antonyms, String example){
        this.word = word;
        this.phonetic = phonetic;
        this.audio = audio;
        this.speech = speech;
        this.definition = definition;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
        this.example = example;
    }
    public String getWord(){return word;}
    public String getPhonetic(){return phonetic;}
    public String getAudio(){return audio;}
    public String getSpeech(){return speech;}
    public String getDefinition(){return definition;}
    public String getSynonyms(){return synonyms;}
    public String getAntonyms(){return antonyms;}
    public String getExample(){return example;}
}