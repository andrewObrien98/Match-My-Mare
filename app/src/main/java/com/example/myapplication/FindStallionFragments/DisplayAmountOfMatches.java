package com.example.myapplication.FindStallionFragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.LoginFragments.LoginMainScreen;
import com.example.myapplication.Model.Stallion;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.StallionViewModel;

public class DisplayAmountOfMatches extends Fragment {
    DisplayAmountOfMatches(){
        super(R.layout.display_amount_of_matches);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //here we set the amount of stallions
        StallionViewModel viewModel = new ViewModelProvider(requireActivity()).get(StallionViewModel.class);
        TextView amount = view.findViewById(R.id.text_amount_of_results);
        amount.setText(viewModel.getStallions().length + "");

        TextView matches = view.findViewById(R.id.text_matches_phrase);
        if(viewModel.getStallions().length < 2){
            matches.setText("Match");
        }

        //if the continue they will go forward
        view.findViewById(R.id.button_display_matches).setOnClickListener(button -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_home, new ViewPagerTabLayoutDisplayStallion(), null)
                    .commit();
        });

        //if they press back they can continue to click on options
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
