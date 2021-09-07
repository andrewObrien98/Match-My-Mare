package com.example.myapplication.LoginFragments;

import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.Objects;

public class LoginMainScreen extends Fragment {
    public LoginMainScreen(){
        super(R.layout.login_main_screen);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //if we click on Sign in we go to the sign in page
        view.findViewById(R.id.button_main_sign_in).setOnClickListener(button -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.fragment_container_login, new SignIn(), null)
                    .commit();
        });

        //if we click on sign up we go to the sign up page
        view.findViewById(R.id.button_main_sign_up).setOnClickListener(button -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.fragment_container_login, new SignUp(), null)
                    .commit();
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_login, new LoginMainScreen(), null)
                        .commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);
    }
}
