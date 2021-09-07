package com.example.myapplication.FindStallionFragments;

import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class DisplayErrorMessage extends Fragment {
    DisplayErrorMessage(){
        super(R.layout.display_no_stallion_found);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //if they want to retry it just sends them back to where it shows the subjects and options
        view.findViewById(R.id.button_retry).setOnClickListener(button -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_home, new ViewPagerTabLayoutDisplaySubjects(), null)
                    .commit();
        });

        //if they press the back button it just sends them back to where it shows the subjects and options
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_home, new ViewPagerTabLayoutDisplaySubjects(), null)
                        .commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);

    }
}
