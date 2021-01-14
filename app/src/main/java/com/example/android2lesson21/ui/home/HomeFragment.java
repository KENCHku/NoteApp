package com.example.android2lesson21.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android2lesson21.MainActivity;
import com.example.android2lesson21.R;
import com.example.android2lesson21.models.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter();

        //hw
        add10();

    }
//for 10 default
    private void add10() {
        for (int i = 10; i > 0; i--) {
            //on 51 that's for time view
            String date = java.text.DateFormat.getDateTimeInstance().format(new Date());
            adapter.addItem(new Note("This is note: " + i,", date: "+date));
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.fab).setOnClickListener(v -> openForm());
        setFragmentListener();
        initList();
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        //underline
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
  //long click removing & how position by toast
        //is too hw
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                // here you get the note that you have clicked on the list

                Note note = adapter.getItem(position);
                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
            }//done


            //here is removing on long click
            @Override
            public void longClick(int position) {// StackOverFlow: Closing a custom alert dialog on button click
                       final int which_item = position;

                        new AlertDialog.Builder(getContext())
                                .setIcon(android.R.drawable.ic_delete)
                                .setTitle("Are you sure?")
                                .setMessage("Do you want to delete this item?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        adapter.remove(which_item);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                });
                 }



                 //get the text
    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener(
                "rk_form",
                getViewLifecycleOwner(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = (Note) result.getSerializable("note");
                        adapter.addItem(note);
                        // Log.e("Home", "text = "+result.getString("text"));

                    }
                });
    }






    private void openForm() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.formFragment);
    }
}










    //example
     /* textTitle.setOnLongClickListener(new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            final int which_item = position;

            new AlertDialog.Builder(getContext())
                    .setIcon(android.R.drawable.ic_delete)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this item?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            list.remove(which_item);
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }
    });*/



