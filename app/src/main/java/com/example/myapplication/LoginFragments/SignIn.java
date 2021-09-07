package com.example.myapplication.LoginFragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.FindStallionFragments.MainFragment;
import com.example.myapplication.FindStallionFragments.SelectCriteria;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.UserViewModel;

public class SignIn extends Fragment {
    SignIn(){
        super(R.layout.login_sign_in);
    }

    boolean readyToMoveOn = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserViewModel viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        Button signInButton = view.findViewById(R.id.button_sign_in);
        Button signUpButton = view.findViewById(R.id.button_go_to_sign_up);

        signInButton.setOnClickListener(button -> {
            EditText emailET = view.findViewById(R.id.edit_text_sign_in_email);
            String email = emailET.getText().toString();
            EditText passwordET = view.findViewById(R.id.edit_text_sign_in_password);
            String password = passwordET.getText().toString();
            viewModel.checkUser(email, password);
        });

        signUpButton.setOnClickListener(button -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.fragment_container_login, new SignUp(), null)
                    .commit();
        });

        viewModel.getReadyToMoveOnSignIn().observe(getViewLifecycleOwner(), ready -> {
            if(ready && !readyToMoveOn){
                signInButton.setEnabled(false);
                signUpButton.setEnabled(false);
                readyToMoveOn = ready;
            } else if(!ready && readyToMoveOn){
                if(viewModel.getUserExists()){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .replace(R.id.fragment_container_login, new MainFragment(), null)
                            .commit();
                } else {
                    TextView status = view.findViewById(R.id.text_sign_in_status);
                    status.setText("Invalid username or password");
                }
                signInButton.setEnabled(true);
                signUpButton.setEnabled(true);
                readyToMoveOn = ready;
            }
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
