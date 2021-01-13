package com.example.android2lesson21.ui.profile;

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


import com.example.android2lesson21.R;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {


    int REQUEST_CODE = 2;


    private ImageView imageView;

    private ProfileViewModel profileViewModel;
    private String uriSaver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

                profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);//bc there is root<--
        final TextView textView = root.findViewById(R.id.text_profile);

        imageView = root.findViewById(R.id.imageView);
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

        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
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