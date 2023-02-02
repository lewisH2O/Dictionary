package com.example.a413project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
    }

    public boolean onTouchEvent(MotionEvent event){
        try {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                showMain();
            return true;
        }catch (Exception e){System.out.println(e);}
        return true;
    }

    private void showMain() {
        Intent intent = new Intent(MainActivity.this, DictionaryActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        finish();
    }
}