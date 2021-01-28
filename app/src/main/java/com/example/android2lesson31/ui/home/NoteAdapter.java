package com.example.android2lesson31.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android2lesson31.R;
import com.example.android2lesson31.models.Note;
import com.example.android2lesson31.OnItemClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {


    private ArrayList<Note> list;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public NoteAdapter(Context context) {
        list = new ArrayList<>();
        this.context = context;

        //task 1
        String date = java.text.DateFormat.getDateTimeInstance().format(new Date());
        list.add(new Note("c, date: ", date));
        list.add(new Note("d, date: ", date));
        list.add(new Note("e, date: ", date));
        list.add(new Note("b, date: ", date));
        list.add(new Note("f, date: ", date));
        list.add(new Note("a, date: ", date));
        list.add(new Note("d, date: ", date));
        list.add(new Note("z, date: ", date));
        list.add(new Note("k, date: ", date));
        list.add(new Note("w, date: ", date));

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
        list.add(0,note);
        notifyItemInserted(list.indexOf(0));
        //to the new note will join to the list with animation

        //notifyItemInserted(list.size() - 1); // here you will get the last position
        //notifyItemInserted(list.indexOf(note)); these are the same
    }

    //================================here is homework tasks================
   /* @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {// -- refreshed: this is un needy think
        super.registerAdapterDataObserver(observer);
    }
*/

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // returns note----finds by indexes then
    public Note getItem(int position) {
        return list.get(position);
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void setList(List<Note> list) {

        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void updateItem(int pos, Note note) {
        list.set(pos,note);
        notifyItemChanged(pos);
    }

    public void sortList(List<Note> sort){
        list.clear();
        list.addAll(sort);
        notifyDataSetChanged();
    }

    public void sortListByDate(List<Note> sortAllByDate) {
        list.clear();
        list.addAll(sortAllByDate);
        notifyDataSetChanged();
    }

    //===============================ViewHolder===================================

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle, textDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitleList);
            textDate=itemView.findViewById(R.id.textDate);
            //================================here is homework's tasks================
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(getAdapterPosition());//кладет позицию
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
            textDate.setText("date: "+note.getDate());

        }
    }
}








