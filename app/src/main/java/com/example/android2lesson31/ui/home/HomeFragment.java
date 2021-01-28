package com.example.android2lesson31.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android2lesson31.App;
import com.example.android2lesson31.Prefs;
import com.example.android2lesson31.R;
import com.example.android2lesson31.models.Note;
import com.example.android2lesson31.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HomeFragment extends Fragment {

    private boolean update = false;
    private int pos;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private Prefs prefs;

    // this method runs once only when you launch the app
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        adapter = new NoteAdapter(getContext());
        loadData();
        /* add10();*/
    }

    /* private void add10() {
        for (int i = 10; i > 0; i--) {
            String date = java.text.DateFormat.getDateTimeInstance().format(new Date());
            adapter.addItem(new Note("This is note " + i, date));
        }
    }*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);

        view.findViewById(R.id.fab).setOnClickListener(v -> {
            update = false;
            openForm(null);
        });
        setFragmentListener();
        initList();
    }

    private void loadData() {
        List<Note> list = App.getAppDatabase().noteDao().getAll();
        adapter.setList(list);
    }


    private void initList() {
        recyclerView.setAdapter(adapter);
        //underline
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        //long click removing & show position by toast
        //is too hw
        adapter.setOnItemClickListener(new OnItemClickListener() {// connecting of two interfaces
            @Override
            public void onClick(int position) {
                // here you get the note that you have clicked on the list
               pos=position;
                update = true;
                Note note = adapter.getItem(position);
                openForm(note);


                //  Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
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
                               App.getAppDatabase().noteDao().delete(adapter.getItem(pos));
                               App.getAppDatabase().noteDao().update(adapter.getItem(pos));
                                adapter.remove(pos);

                              /*  if (list.size()>0) {
                                    App.getAppDatabase().noteDao().delete(list.get(position));
                                }*/
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }


    private void openForm(Note note) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("note", note);
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigate(R.id.formFragment, bundle);
    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener(
                "rk_form",
                getViewLifecycleOwner(),
                (requestKey, result) -> {
                    Note note = (Note) result.getSerializable("note");
                    if (update) {
                        adapter.updateItem(pos,note);
                    } else {
                        adapter.addItem(note);
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu,
                                    @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        prefs = new Prefs(requireContext());

        switch (item.getItemId()) {
            case R.id.clear_settings:
               alertDialogClearSettings();
                return true;


            case R.id.sortByA_Z:
               SortByA_Z();
                return true;

            case  R.id.sortByDate:
                SortDyDate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SortDyDate() {
        adapter.sortListByDate(App.getAppDatabase().noteDao().sortAllByDate());
    }

    private void SortByA_Z() {
        adapter.sortList(App.getAppDatabase().noteDao().sortAll());
    }

    private void alertDialogClearSettings() {
        new AlertDialog.Builder(getContext())
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Are you sure?")
                .setMessage("Do you want to clear settings?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        prefs.clearSettings();
                        requireActivity().finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}






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

/*  list = (ArrayList<Note>) App.getAppDatabase().noteDao().getAll();

        if (click) {
        click = false;

        Collections.sort(list, new Comparator<Note>() {
@Override
public int compare(Note o1, Note o2) {
        if (o1.getTitle() == null || o2.getTitle() == null)
        return 0;
        return o1.getTitle().compareTo(o2.getTitle());
        }
        });
        } else {
        Collections.reverse(list);
        click = true;
        }
        adapter.setList(list); а-я*/
/*list = (ArrayList<Note>) App.getAppDatabase().noteDao().getAll();
        if (clickForDate) {
        clickForDate = false;
        Collections.sort(list, new Comparator<Note>() {
@Override
public int compare(Note o1, Note o2) {
        if (o1.getDate() == null || o2.getDate() == null)
        return 0;
        return o1.getDate().compareTo(o2.getDate());
        }
        });
        } else {
        Collections.sort(list, new Comparator<Note>() {
@Override
public int compare(Note o1, Note o2) {
        if (o1.getDate() == null || o2.getDate() == null)
        return 0;
        return o2.getDate().compareTo(o1.getDate());
        }
        });

        clickForDate = true;
        }
        adapter.setList(list); дата*/

