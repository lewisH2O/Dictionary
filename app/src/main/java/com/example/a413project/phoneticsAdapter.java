package com.example.a413project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class phoneticsAdapter extends RecyclerView.Adapter<phoneticsAdapter.ViewHolder> {

    private wordData wd;
    phoneticsAdapter(wordData wd) {
        this.wd = wd;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView phonetic;
        private TextView audio;

        ViewHolder(View itemView) {
            super(itemView);
            phonetic = (TextView) itemView.findViewById(R.id.text);
            audio = (TextView) itemView.findViewById(R.id.audio);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phonetics_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.phonetic.setText(wd.getPhonetic().get(position));
//        holder.audio.setText(wd.getAudio().get(position));
    }
    @Override
    public int getItemCount()
    {
        return wd.getPhoneticsSize();
    }
}