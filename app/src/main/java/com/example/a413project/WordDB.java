package com.example.a413project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class WordDB extends SQLiteOpenHelper {
    private Context context;
    private	static final int DATABASE_VERSION =	1;
    private static final String DATABASE_NAME = "words.db"; // db name
    private static final String TABLE_NAME = "words"; // table name

    private static final String COLUMNS_WORD = "word";
    private static final String COLUMNS_PHONETIC = "phonetic";
    private static final String COLUMNS_AUDIO = "audio";
    private static final String COLUMNS_SPEECH = "speech";
    private static final String COLUMNS_DEFINITION = "definition";
    private static final String COLUMNS_SYNONYMS = "synonyms";
    private static final String COLUMNS_ANTONYMS = "antonyms";
    private static final String COLUMNS_EXAMPLE = "example";

    WordDB(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table creation sql statement
        final String creationQUery = "create table if not exists " + TABLE_NAME + "( "
                + COLUMNS_WORD + " text primary key, "
                + COLUMNS_PHONETIC + " text, "
                + COLUMNS_AUDIO + " text, "
                + COLUMNS_SPEECH + " text, "
                + COLUMNS_DEFINITION + " text, "
                + COLUMNS_SYNONYMS + " text, "
                + COLUMNS_ANTONYMS + " text, "
                + COLUMNS_EXAMPLE + " text not null" + ")";
        db.execSQL(creationQUery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void getAllWord(WordList wordList) {
        wordList.clear();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String word = cursor.getString(0);
            String phonetic = cursor.getString(1);
            String audio = cursor.getString(2);
            String speech = cursor.getString(3);
            String definition = cursor.getString(4);
            String synonyms = cursor.getString(5);
            String antonyms = cursor.getString(6);
            String example = cursor.getString(7);
            wordList.addWord(word, phonetic, audio, speech, definition, synonyms, antonyms, example);
        }
        cursor.close();
    }

    //add to favourite List
    void addWord(String word, String phonetic, String audio, String speech, String definition, String synonyms, String antonyms, String example) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNS_WORD, word);
        values.put(COLUMNS_PHONETIC, phonetic);
        values.put(COLUMNS_AUDIO, audio);
        values.put(COLUMNS_SPEECH, speech);
        values.put(COLUMNS_DEFINITION, definition);
        values.put(COLUMNS_SYNONYMS, synonyms);
        values.put(COLUMNS_ANTONYMS, antonyms);
        values.put(COLUMNS_EXAMPLE, example);
        db.insert(TABLE_NAME,null, values);
        Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
    }


    //search if in favourite list
    public boolean searchWord(String word){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = { word };
        Cursor cursor = db.query(TABLE_NAME, null, COLUMNS_WORD + " =?", args, null, null, null);
        Word target = null;
        boolean result=false;
        if	(cursor.moveToFirst()){
            String phonetic = cursor.getString(1);
            String audio = cursor.getString(2);
            String speech = cursor.getString(3);
            String definition = cursor.getString(4);
            String synonyms = cursor.getString(5);
            String antonyms = cursor.getString(6);
            String example = cursor.getString(6);
            target = new Word(word, phonetic, audio, speech, definition, synonyms, antonyms, example);
            result=true;
        }
        cursor.close();
        return result;
    }

    // Delete job with job id
    public void deleteWord(String word){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMNS_WORD	+ "	= ?", new String[] { word});
    }

}
