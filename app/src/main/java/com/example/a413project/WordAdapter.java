package com.example.a413project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ComponentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class WordAdapter extends RecyclerView.Adapter<com.example.a413project.WordAdapter.ViewHolder> {
    private Activity activity;
    private Context context;
    private WordList wordList;

    public WordAdapter(Activity activity, Context context, WordList wordList){
        this.activity = activity;
        this.context = context;
        this.wordList = wordList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.simple_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Word target = wordList.getItems()[position];
        holder.wordView.setText(target.getWord());
        holder.wordView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context,DictionaryActivity.class);
                intent.putExtra("word", target.getWord());

                activity.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Favourite)context).deleteWord(target.getWord());
            }
        });
    }

    @Override
    public int getItemCount() {
        return wordList.getItems().length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView wordView;
        ImageButton delete;
        LinearLayout mainLayout;
        ViewHolder(View itemView) {
            super(itemView);
            wordView = itemView.findViewById(R.id.infoWord);
            delete = itemView.findViewById(R.id.infoDelete);
            mainLayout = itemView.findViewById(R.id.info);
        }
    }

}
