package com.example.a413project;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Favourite extends AppCompatActivity {
    private WordDB wordDB;
    private WordList wordList = new WordList();
    private WordAdapter adapter;
    private RecyclerView favourite_view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        favourite_view = findViewById(R.id.favouriteList);
        adapter = new WordAdapter(Favourite.this, this, wordList);

        wordDB = new WordDB(Favourite.this);
        wordDB.getAllWord(wordList);

        favourite_view.setAdapter(adapter);
        favourite_view.setLayoutManager(new LinearLayoutManager(this));

        adapter.notifyDataSetChanged();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==R.id.dictionary){
            Intent intent = new Intent(Favourite.this, DictionaryActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
        }else if (item.getItemId()==R.id.favourite) {
            Toast toast = Toast.makeText(this, "you're already in My Word List", Toast.LENGTH_LONG);
            toast.show();

        }else if (item.getItemId()==R.id.about){
            new AlertDialog.Builder(this)
                    .setTitle("Dictionary")
                    .setMessage("You can search words and put it into your notebook.\n" +"\n"+
                            "Lau Tsz Ho 12573120 \n" +
                            "Lee Chi San 12539832 \n")
                    .setNegativeButton(android.R.string.ok, null)
                    .show();

        }
        return false;
    }



    public void deleteWord(String word) {
        wordDB.deleteWord(word);
        wordDB.getAllWord(wordList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        wordDB.getAllWord(wordList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(wordDB != null){
            wordDB.close();
        }
    }

}
