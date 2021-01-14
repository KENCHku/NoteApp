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
import java.util.Date;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {


    private ArrayList<Note> list;
    //hw
    private OnItemClickListener onItemClickListener;
    //hw


    public NoteAdapter() {
        list = new ArrayList<>();

        //task 1
        String date = java.text.DateFormat.getDateTimeInstance().format(new Date());
        list.add(new Note("one, date: ", date));
        list.add(new Note("two, date: ", date));
        list.add(new Note("three, date: ", date));
        list.add(new Note("four, date: ", date));
        list.add(new Note("five, date: ", date));
        list.add(new Note("six, date: ", date));
        list.add(new Note("seven, date: ", date));
        list.add(new Note("eight, date: ", date));
        list.add(new Note("nine, date: ", date));
        list.add(new Note("ten, date: ", date));

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
            holder.itemView.setBackgroundColor(Color.parseColor("#FFBB86FC"));
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
    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {//for the time view
        super.registerAdapterDataObserver(observer);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // returns note
    public Note getItem(int position) {
        return list.get(position);
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
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
            textTitle.setText(note.getTitle()+" "+note.getDate());

        }
    }
}








