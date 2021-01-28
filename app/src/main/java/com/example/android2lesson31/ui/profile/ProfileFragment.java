package com.example.android2lesson31.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;


import com.airbnb.lottie.LottieAnimationView;
import com.example.android2lesson31.ProfileAdapter;
import com.example.android2lesson31.R;
import com.example.android2lesson31.models.Jobs;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {


    int REQUEST_CODE = 2;

    LottieAnimationView animation1, animation2, animation3;

    private ImageView imageView;

    private ProfileViewModel profileViewModel;
    private String uriSaver;
    private RecyclerView recyclerView;
    private ProfileAdapter profileAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileAdapter=new ProfileAdapter(getContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

                profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);//bc there is root<--

      /*  animation1=root.findViewById(R.id.lottie1);
        animation2=root.findViewById(R.id.lottie2);
        animation3=root.findViewById(R.id.lottie3);

        animation1.setAnimation(R.raw.designer);
        animation2.setAnimation(R.raw.dev);
        animation3.setAnimation(R.raw.boxing);*/



        recyclerView = root.findViewById(R.id.recyclerProfile);

        recyclerView.setAdapter(profileAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));


        addListByModel();



        imageView = root.findViewById(R.id.imageSelect);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override//for image
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                // указываем действие
                intent.setType("image/*");
                // указываем тип, то есть Image
                startActivityForResult(intent, REQUEST_CODE);
                // тут уже переходит в галлерею
            }
        });
        return root;
    }

    private void addListByModel() {
        ArrayList<Jobs> list = new ArrayList<>();
        list.add(new Jobs("UX/UI","I have some skills", R.raw.designer));
        list.add(new Jobs("Sportsman","My hobby is sport", R.raw.boxing));
        list.add(new Jobs("Android developer","Senior android developer", R.raw.dev));
        profileAdapter.addDataToList(list);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();
            uriSaver = String.valueOf(uri);//uri надо перевести в стринг чтобы перевести через интент
            imageView.setImageURI(uri);
        }
    }
}