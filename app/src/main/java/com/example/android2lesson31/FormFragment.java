package com.example.android2lesson31;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android2lesson31.R;
import com.example.android2lesson31.models.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;


public class FormFragment extends Fragment {

    private Note note;
    private EditText editText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("KKU", "Form Fragment");
        editText = view.findViewById(R.id.editText);
        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();

            }
        });

        note = (Note) requireArguments().getSerializable("note");
        if (note != null)
            editText.setText(note.getTitle());
    }

    private void save() {

        String text = editText.getText().toString().trim();//trim removes odd spaces!!!
        //in programming space is too as symbol !!! !!! !!!

        String date = java.text.DateFormat.getDateTimeInstance().format(new Date());
        if (note == null) {
            note = new Note(text, date);

             saveToFirestore(note);
            App.getAppDatabase().noteDao().insert(note);
        } else {
            note.setTitle(text);
            App.getAppDatabase().noteDao().update(note);
            updateFireStore(note);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("note", note);
        getParentFragmentManager().setFragmentResult("rk_form", bundle);
        close();
    }

    private void updateFireStore(Note note) {
     FirebaseFirestore db = FirebaseFirestore.getInstance();

     DocumentReference noteRef = db.collection("notes")
             .document(note.getNoteId());

     noteRef.update("title", note.getTitle()).addOnCompleteListener(new OnCompleteListener<Void>() {
         @Override
         public void onComplete(@NonNull Task<Void> task) {

             if (task.isSuccessful()){

                 App.getAppDatabase().noteDao().update(note);
                 Log.e("KKU", "onComplete: Updated  to" + note.getTitle());
                 close();
             }else{
                 Log.e("KKU", "onComplete:  failed to update");

             }
         }
     });
    }

    private void saveToFirestore(Note note) {
        FirebaseFirestore.getInstance()
                .collection("notes")
                .add(note)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show();
                    Log.e("KKU", "onComplete: Note has been added");
                    close();
                }else
                    Log.e("KKU", "onComplete: is failed");


            }
        });
    }


    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigateUp();
    }
}