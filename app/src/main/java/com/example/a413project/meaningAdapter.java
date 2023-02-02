package com.example.a413project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class meaningAdapter extends RecyclerView.Adapter<meaningAdapter.ViewHolder> {

    private wordData wd;
    meaningAdapter(wordData wd) {
        this.wd = wd;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView speech;
        private TextView definition;
        private TextView example;
        private TextView synonyms;
        private TextView antonyms;

        ViewHolder(View itemView) {
            super(itemView);
            speech = (TextView) itemView.findViewById(R.id.partOfSpeech);
            definition=(TextView) itemView.findViewById(R.id.definition);
            example= (TextView) itemView.findViewById(R.id.example);
            synonyms = (TextView) itemView.findViewById(R.id.synonyms);
            antonyms = (TextView) itemView.findViewById(R.id.antonyms);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meaning_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //if(!wd.getSpeech().get(position).isEmpty())
        {
            holder.speech.setText("partOfSpeech: "+wd.getSpeech().get(position));
        }
        //if(!wd.getDefinition().get(position).isEmpty())
        {
            holder.definition.setText("Definition: "+ wd.getDefinition().get(position));
        }
        //if(!wd.getExample().get(position).isEmpty())
        {
            holder.example.setText("Example: "+ wd.getExample().get(position));
        }
        //if(!wd.getSynonyms().get(position).isEmpty())
        {
            holder.synonyms.setText("Synonyms: "+ wd.getSynonyms().get(position));
        }
        //if(!wd.getAntonyms().get(position).isEmpty())
        {
            holder.antonyms.setText("Antonyms: "+ wd.getAntonyms().get(position));
        }
    }
    @Override
    public int getItemCount() {
        return wd.getMeaningSize();
    }
}