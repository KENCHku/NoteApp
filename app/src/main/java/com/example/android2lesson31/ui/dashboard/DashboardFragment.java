package com.example.android2lesson31.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android2lesson31.OnItemClickListener;
import com.example.android2lesson31.R;
import com.example.android2lesson31.models.Note;
import com.example.android2lesson31.ui.home.NoteAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.List;
public class DashboardFragment extends Fragment {

    private NoteAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter(getContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        initList();
        loadData();
    }

    private void loadData() {
      CollectionReference ref =  FirebaseFirestore.getInstance().collection("notes");
                ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                   /* List<Note>notes = new ArrayList<>();
                    for (DocumentSnapshot snapshot : task.getResult()) {
                        String docId =snapshot.getId();
                        Note note = snapshot.toObject(Note.class);
                        note.setDocumentId(docId);
                        notes.add(note);//when we need id
                    }*/
                    List<Note> list = task.getResult().toObjects(Note.class);
                    adapter.setList(list);
                }
            }
        });
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void longClick(int position) {

            }
        });
    }
}

