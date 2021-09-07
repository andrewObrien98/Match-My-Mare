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
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.UserViewModel;

public class SignUp extends Fragment {

    SignUp(){
        super(R.layout.login_sign_up);
    }

    boolean readyToMove = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserViewModel viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        view.findViewById(R.id.button_sign_up).setOnClickListener(button -> {
            EditText passwordET = view.findViewById(R.id.edit_text_sign_up_password);
            String password = passwordET.getText().toString();
            EditText passwordConfirmET = view.findViewById(R.id.edit_text_sign_up_password_check);
            String passwordConfirm = passwordConfirmET.getText().toString();
            EditText emailET = view.findViewById(R.id.edit_text_sign_up_email);
            String email = emailET.getText().toString();
            EditText nameET = view.findViewById(R.id.edit_text_sign_up_name);
            String name = nameET.getText().toString();
            TextView status = view.findViewById(R.id.text_sign_up_status);

            //check to make sure that the password are identical, over 7 characters long, and that its a valid email
            if(!viewModel.isValidEmail(email)){
                status.setText("Invalid email");
            } else if(!viewModel.checkPassword(password)){
                status.setText("Password must be at least 7 characters");
            } else if(password.equals(passwordConfirm)){
                viewModel.checkNewUser(name, email, password);
            } else {
                status.setText("Duplicate Passwords");
            }
        });

        view.findViewById(R.id.button_go_to_sign_in).setOnClickListener(button -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.fragment_container_login, new SignIn(), null)
                    .commit();
        });

        viewModel.getReadyToMoveOnSignUp().observe(getViewLifecycleOwner(), ready -> {
            Button signUpButton = view.findViewById(R.id.button_sign_up);
            Button signInButton = view.findViewById(R.id.button_go_to_sign_in);
            if(ready && !readyToMove){
                signUpButton.setEnabled(false);
                signInButton.setEnabled(false);
                readyToMove = ready;
            } else if(!ready && readyToMove){
                if(viewModel.getUserIsUnique()){
                    viewModel.setUserIsUnique(false);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(null)
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container_login, new MainFragment(), null)
                            .commit();
                } else {
                    TextView status = view.findViewById(R.id.text_sign_up_status);
                    status.setText("Email already exists");
                }
                signUpButton.setEnabled(true);
                signInButton.setEnabled(true);
                readyToMove = ready;
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
