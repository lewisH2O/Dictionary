package com.example.a413project;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.net.Uri;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetWordTask implements Runnable {

    String word;
    //https://api.dictionaryapi.dev/api/v2/entries/en/+word
    private static final String url = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    int phoneticsSize=0;
    int meaningsSize=0;
    int definitionsSize=0;
    ArrayList<String> phonetic = new ArrayList<>();
    ArrayList<String> audio = new ArrayList<>();
    ArrayList<String> speech = new ArrayList<>();
    ArrayList<String> definition = new ArrayList<>();
    ArrayList<String> synonyms = new ArrayList<>();
    ArrayList<String> antonyms = new ArrayList<>();
    ArrayList<String> example = new ArrayList<>();
    ArrayList<JSONArray> definitions = new ArrayList<>();

    String origin;

    public GetWordTask(String word) {
        this.word = word;
    }

    public void retrieveWordDetails(String result) {

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray phonetics =jsonObject.getJSONArray("phonetics");
            phoneticsSize=phonetics.length();
            for (int i = 0; i<phonetics.length(); i++){
                JSONObject jo = phonetics.getJSONObject(i);
                if(jo.has("text"))
                {
                    String au = jo.getString("text");
                    phonetic.add(au);
                }else {phonetic.add("");}
                if(jo.has("audio"))
                {
                    String au = jo.getString("audio");
                    audio.add(au);
                }{audio.add("");}
            }
            JSONArray meanings =jsonObject.getJSONArray("meanings");
            meaningsSize=meanings.length();
            for(int i = 0; i<meanings.length(); i++)
            {
                JSONObject jo = meanings.getJSONObject(i);
                if (jo.has("partOfSpeech"))
                {
                    String au = jo.getString("partOfSpeech");
                    speech.add(au);
                }else {speech.add("");}
                if (jo.has("definitions"))
                {
                    JSONArray ja2 = jo.getJSONArray("definitions");
                    definitionsSize=ja2.length();
                    for (int ii = 0; ii<ja2.length(); ii++)
                    {
                        JSONObject jo2 = ja2.getJSONObject(ii);
                        if(jo2.has("definition"))
                        {
                            String au = jo2.getString("definition");
                            definition.add(au);
                        }else {definition.add("");}
                        if(jo2.has("example"))
                        {
                            String au = jo2.getString("example");
                            example.add(au);
                        }else {example.add("");}
                    }
                }
                if (jo.has("synonyms"))
                {
                    String au = jo.getString("synonyms");
                    au=removeFirstandLast(au);
                    synonyms.add(au);
                }else {synonyms.add("");}
                if (jo.has("antonyms"))
                {
                    String au = jo.getString("antonyms");
                    au=removeFirstandLast(au);
                    antonyms.add(au);
                }else {antonyms.add("");}
            }

            System.out.println("phonetic "+phonetic);
            System.out.println("audio "+audio);
            System.out.println("speech "+speech);
            System.out.println("definition "+definition);
            System.out.println("synonyms "+synonyms);
            System.out.println("antonyms "+antonyms);
            System.out.println("example "+example);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void run(){

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String wordJSONString = null;
        try {
            URL requestURL = new URL(url + word);
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);

                builder.append("\n");
            }
            if (builder.length() == 0) {
                return;
            }
            wordJSONString = builder.toString();
            wordJSONString=removeFirstandLast(wordJSONString);
            retrieveWordDetails(wordJSONString);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    private String removeFirstandLast(String str)
    {
        str = str.substring(1, str.length() - 1);
        return str;
    }
    public wordData getword()
    {
        wordData ed = new wordData(
                phoneticsSize,meaningsSize,definitionsSize,
                phonetic,audio,speech,definition,synonyms,antonyms,example
        );
        return ed;
    }
}
