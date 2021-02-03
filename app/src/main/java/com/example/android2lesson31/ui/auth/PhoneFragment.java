package com.example.android2lesson31.ui.auth;


import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;
import com.example.android2lesson31.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneFragment extends Fragment {

    private TextView redText;

    private View viewPhone;
    private View viewCode;

    private EditText editPhone;
    private EditText editCode;

    private TextView textTimer;
    private CountDownTimer timer;
    private boolean isCodeSent = false;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String verificationId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editPhone = view.findViewById(R.id.editPhone);
        editCode = view.findViewById(R.id.editCode);


        redText = view.findViewById(R.id.redText);

        viewPhone = view.findViewById(R.id.viewPhone);
        viewCode = view.findViewById(R.id.viewCode);

        textTimer = view.findViewById(R.id.textTimer);

        setListeners(view);
        setCallbacks();
        redText.setVisibility(View.GONE);
        initView();
    }

    private void setListeners(View view) {

        view.findViewById(R.id.btnNext).setOnClickListener(v -> requestSms());


        view.findViewById(R.id.btnSignIn).setOnClickListener(v -> confirm());

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

    private void confirm() {
        String code = editCode.getText().toString();
        if (code.length() == 6 && TextUtils.isDigitsOnly(code)) {

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signIn(credential);
        }else
            Toast.makeText(requireActivity(), "Fill the code!",Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        viewCode.setVisibility(View.GONE);
        showViewPhone();
    }

    private void showViewPhone() {
        viewPhone.setVisibility(View.VISIBLE);
        viewCode.setVisibility(View.GONE);
    }

    private void showViewCode() {
        viewCode.setVisibility(View.VISIBLE);
        viewPhone.setVisibility(View.GONE);

        startTimer();
    }

    private void startTimer() {
         timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                String text = "00:" + (millisUntilFinished / 1000);
                textTimer.setText(text);
            }

            @Override
            public void onFinish() {

                showViewPhone();
                redText.setVisibility(View.VISIBLE);
            }
        };
        timer.start();
    }




    private void setCallbacks() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                if (isCodeSent){
                    editCode.setText(phoneAuthCredential.getSmsCode());//self automatically writes the code
                }else {
                    signIn(phoneAuthCredential);
                }
                Log.e("TAG", "onVerificationCompleted");
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("TAG", "onVerificationFailed" + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                isCodeSent=true;

                verificationId = s;
                ////////////*//*//*////////
                showViewCode();
                ////////////*//*//*////////

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }
        };
    }
//создает-добавляет юзера в файрбэйз по номеру если комплетед
    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                   if (timer!=null){
                       timer.cancel();
                   }

                    close();
                } else {
                    Toast.makeText(requireContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestSms() {
      /* String phone = editPhone.getText().toString();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())                 // Activity (for callback binding)
                        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Toast.makeText(requireContext(), "wait!", Toast.LENGTH_SHORT).show();
*/
        showViewCode();
    }

    private void close() {
        // ((MainActivity) requireActivity()).closeFragment();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();

    }
}