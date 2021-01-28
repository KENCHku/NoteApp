package com.example.android2lesson31.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.android2lesson31.R;
import com.example.android2lesson31.models.BoardData;
import com.example.android2lesson31.OnItemClickListener;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

   private String[] titles =  new String[]{"Fast", "Free", "Powerful"};
    private String[] descriptions =  new String[]{"fast optimized app", "free and available", "less volume more power"};
    private int[] imageBoards =  new int[]{R.drawable.fast, R.drawable.free, R.drawable.powerful};

    private OnItemClickListener onItemClickListener;

    private ArrayList<BoardData> list = new ArrayList<>();


    public BoardAdapter() {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return 3;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addDataToBoard(ArrayList<BoardData> boardData) {
       this.list= boardData;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageBoard;
        private TextView textTitle, textDesc;
        private Button btnStart;
        private LottieAnimationView lottieAnimationView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBoard=itemView.findViewById(R.id.imageSelect);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc=itemView.findViewById(R.id.textDesc);

            lottieAnimationView=itemView.findViewById(R.id.lottie_layer_name);

            btnStart = itemView.findViewById(R.id.btnStart);

            btnStart.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
        }

        public void bind(int position) {
            btnStart.setVisibility(View.GONE);

            //here we set our model by positions and by its fields
            show(position);
        }

        private void show(int position) {
            //shows button on the third board only
            if (position==2){
            btnStart.setVisibility(View.VISIBLE);
        }
            //here we set our model by positions and by its fields
            BoardData boardData = list.get(position);

            lottieAnimationView.setAnimation(boardData.getImageResourceId());
/*
            imageBoard.setImageResource(boardData.getImageResourceId());
*/
            textTitle.setText(boardData.getTitle());
            textDesc.setText(boardData.getDescription());
            /*imageBoard.setImageResource(imageBoards[position]);
            textTitle.setText(titles[position]);
            textDesc.setText(descriptions[position]);*/

        }
    }
}
