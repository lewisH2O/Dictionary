package com.example.a413project;

import java.io.Serializable;
import java.util.ArrayList;

public class wordData implements Serializable {
    int phonetics;
    ArrayList<String> phonetic = new ArrayList<>();
    ArrayList<String> audio = new ArrayList<>();

    int meanings;
    ArrayList<String> speech = new ArrayList<>();
    ArrayList<String> synonyms = new ArrayList<>();
    ArrayList<String> antonyms = new ArrayList<>();

    int definitions;
    ArrayList<String> definition = new ArrayList<>();
    ArrayList<String> example = new ArrayList<>();

    public wordData(
            int phonetics,
            int meanings,
            int definitions,
            ArrayList<String> phonetic,
            ArrayList<String> audio,
            ArrayList<String> speech,
            ArrayList<String> definition,
            ArrayList<String> synonyms,
            ArrayList<String> antonyms,
            ArrayList<String> example
            )
    {
        this.phonetics=phonetics;
        this.meanings=meanings;
        this.definitions=definitions;
        this.phonetic=phonetic;
        this.audio=audio;
        this.speech=speech;
        this.definition=definition;
        this.synonyms=synonyms;
        this.antonyms=antonyms;
        this.example=example;
    }
    public int getPhoneticsSize(){return phonetics;}
    public ArrayList<String> getPhonetic() { return phonetic; }
    public ArrayList<String> getAudio() { return audio; }

    public int getMeaningSize(){return meanings;}
    public ArrayList<String> getSpeech() { return speech; }
    public ArrayList<String> getSynonyms() { return synonyms; }
    public ArrayList<String> getAntonyms() { return antonyms; }

    public int getDefinitionsSize(){return definitions;}
    public ArrayList<String> getDefinition() { return definition; }
    public ArrayList<String> getExample() { return example; }


}
