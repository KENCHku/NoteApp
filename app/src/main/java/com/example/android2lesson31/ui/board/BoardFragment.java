package com.example.android2lesson31.ui.board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android2lesson31.Prefs;
import com.example.android2lesson31.R;
import com.example.android2lesson31.models.BoardData;
import com.example.android2lesson31.OnItemClickListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class BoardFragment extends Fragment {

    private BoardAdapter boardAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //for tab dots
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);


        view.findViewById(R.id.btnSkip).setOnClickListener(v -> close());


        Button btnSkip = view.findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(v -> close());

        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        boardAdapter = new BoardAdapter();

        //addModelToList

        addListByModel();

        viewPager.setAdapter(boardAdapter);
        //for tab locate dots
        new TabLayoutMediator(tabLayout,viewPager, ((tab, position) -> tab.setText(""))).attach();

        boardAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                close();
            }

            @Override
            public void longClick(int position) {

            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                }
        );
    }

    //////////////////////======////////////////////
    private void addListByModel() {
        ArrayList<BoardData> list = new ArrayList<>();
        list.add(new BoardData("Fast",
                "fast optimized app",
                R.raw.fast));
        list.add(new BoardData("Free",
                "free and available",
                R.raw.free));
        list.add(new BoardData("Powerful",
                "less volume more power",
                R.raw.powerful));
        boardAdapter.addDataToBoard(list);
    }

    private void close() {
        Prefs prefs = new Prefs(requireContext());
        prefs.saveBoardsStatus();
        NavController navController = Navigation.findNavController(
                requireActivity(),
                R.id.nav_host_fragment);
        navController.navigateUp();
        navController.navigate(R.id.phoneFragment);
    }
}