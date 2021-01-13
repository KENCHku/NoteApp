package com.example.android2lesson21.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.text.Transliterator;
import android.nfc.tech.NfcA;
import android.preference.DialogPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android2lesson21.MainActivity;
import com.example.android2lesson21.R;
import com.example.android2lesson21.models.Note;
import com.example.android2lesson21.ui.form.FormFragment;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {


    private ArrayList<Note> list;
    //hw
    private OnItemClickListener onItemClickListener;
//hw
    private Context context;

    public NoteAdapter() {
        list = new ArrayList<>();
        this.context = context;
        //task 1
        list.add(new Note("one"));
        list.add(new Note("two"));
        list.add(new Note("three"));
        list.add(new Note("four"));
        list.add(new Note("five"));
        list.add(new Note("six"));
        list.add(new Note("seven"));
        list.add(new Note("eight"));
        list.add(new Note("nine"));
        list.add(new Note("ten"));

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));


        //================================чередование цветов бэкграунда списка================
        if (position % 2 == 1)//чередование цвета бэкграунда у списка
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FF6200EE"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FF018786"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Note note) {
        list.add(note);
        notifyDataSetChanged();
    }
    //================================here is homework tasks================

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // returns note
    public Note getItem(int position) {
        return list.get(position);
    }

    public void remove(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    //===============================ViewHolder===================================

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            //================================here is homework tasks================
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(getAdapterPosition());

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.longClick(getAdapterPosition());
                    return true;
                }
            });



        }

        public void onBind(Note note) {
            textTitle.setText(note.getTitle());
        }
    }
}










