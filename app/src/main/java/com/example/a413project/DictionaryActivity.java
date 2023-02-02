package com.example.a413project;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.IOException;


public class DictionaryActivity extends AppCompatActivity {
    private wordData wd ;
    private RecyclerView phonetics_view;
    private RecyclerView meaning_view;
    private RecyclerView definition_view;

    private phoneticsAdapter phoneticsAdapter;
    private meaningAdapter meaningAdapter;

    Button playBtn;
    MediaPlayer mediaPlayer;
    Button add;
    private String audioUrl;

    WordDB wordDB;

    String favourite=null;
    Boolean isFavourite = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        playBtn = findViewById(R.id.idBtnPlay);
        playBtn.setVisibility(View.GONE);
        add = findViewById(R.id.add);
        add.setVisibility(View.GONE);
        SearchView searchView = findViewById(R.id.searchBar);
        searchView.setIconifiedByDefault(false);
        wordDB = new WordDB(DictionaryActivity.this);
        getAndSetIntentData();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                add.setEnabled(true);
                add.setText("Add Favourite");
                GetWordTask getWordTask = new GetWordTask(query);
                Thread getWordThread = new Thread(getWordTask);
                getWordThread.start();
                try {
                    getWordThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                wd=getWordTask.getword();
                TextView word =findViewById(R.id.name);
                word.setText(query);
                ShowWordPhonetics();
                isFavourite = wordDB.searchWord(query);
                if (isFavourite){
                    add.setEnabled(false);
                    add.setText("Favourite word");
                }
                for(int i=0;i< wd.getPhoneticsSize();i++)
                {
                    audioUrl = wd.getAudio().get(i);
                    System.out.println("audioUrl "+audioUrl);
                    if(!audioUrl.isEmpty())
                    {
                        playBtn.setVisibility(View.VISIBLE);
                        break;
                    }
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView wordView = findViewById(R.id.name);
                wordDB.addWord(wordView.getText().toString(), "ph", "au", "sp", "de", "sy", "an", "ex");
                add.setEnabled(false);
                add.setText("Favourite word");
            }
        });
        playBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                playAudio();
            }
        });
    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("word")){
            GetWordTask getWordTask = new GetWordTask(getIntent().getStringExtra("word"));
            Thread getWordThread = new Thread(getWordTask);
            getWordThread.start();
            try {
                getWordThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wd=getWordTask.getword();
            TextView word =findViewById(R.id.name);
            word.setText(getIntent().getStringExtra("word"));
            ShowWordPhonetics();
            for(int i=0;i< wd.getPhoneticsSize();i++)
            {
                audioUrl = wd.getAudio().get(i);
                System.out.println("audioUrl "+audioUrl);
                if(!audioUrl.isEmpty())
                {
                    playBtn.setVisibility(View.VISIBLE);
                    break;
                }
            }
            add.setEnabled(false);
            add.setText("Favourite word");
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==R.id.dictionary){
            Toast toast = Toast.makeText(this, "you're already in Dictionary page", Toast.LENGTH_SHORT);
            toast.show();
        }else if (item.getItemId()==R.id.favourite) {
            Intent intent = new Intent(DictionaryActivity.this, Favourite.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
        }else if (item.getItemId()==R.id.about){
            new AlertDialog.Builder(this)
                    .setTitle("Dictionary")
                    .setMessage("You can search words and put it into your notebook.\n" +"\n"+
                            "Lee Chi San 12539832 \n" +
                            "Lau Tsz Ho 12573120 \n")
                    .setNegativeButton(android.R.string.ok, null)
                    .show();
        }
        return false;
    }
    public void ShowWordPhonetics()
    {
        phonetics_view = findViewById(R.id.phonetic_recycler);
        phonetics_view.setLayoutManager(new LinearLayoutManager(this));
        phonetics_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        phoneticsAdapter = new phoneticsAdapter(wd);
        phonetics_view.setAdapter(phoneticsAdapter);
        if (phoneticsAdapter.getItemCount() == 0)
        {
            TextView word =findViewById(R.id.name);
            word.setText("Not Found");
            playBtn = findViewById(R.id.idBtnPlay);
            playBtn.setVisibility(View.GONE);
            add = findViewById(R.id.add);
            add.setVisibility(View.GONE);
        }else
        {
            add = findViewById(R.id.add);
            add.setVisibility(View.VISIBLE);
        }
        meaning_view= findViewById(R.id.meaning_recycler);
        meaning_view.setLayoutManager(new LinearLayoutManager(this));
        meaning_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        meaningAdapter = new meaningAdapter(wd);
        meaning_view.setAdapter(meaningAdapter);

    }
    private void playAudio() {
        //TextView audio=findViewById(R.id.audio);
        /*
        phonetics_view = findViewById(R.id.phonetic_recycler);
        for(int i=0;i< wd.getPhoneticsSize();i++)
        {
            audioUrl = ((TextView) phonetics_view.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.audio)).getText().toString();
            System.out.println("audioUrl "+audioUrl);
            if(!audioUrl.isEmpty())
            {
                break;
            }
        }*/
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
    }
}

