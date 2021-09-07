package com.example.myapplication.FindStallionFragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.FindStallionAdapters.ListAdapter;
import com.example.myapplication.FindStallionAdapters.SaveListAdapter;
import com.example.myapplication.Model.Stallion;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.SaveStallionViewModel;
import com.example.myapplication.ViewModel.UserViewModel;

import java.util.Objects;

public class DisplaySaveBox extends Fragment {

    Stallion stallion;
    DisplaySaveBox(Stallion currentStallion){
        super(R.layout.display_save_stallion);
        stallion = currentStallion;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SaveStallionViewModel viewModel = new ViewModelProvider(getActivity()).get(SaveStallionViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        String[] list = new String[1];//right now this list is just of favorite but sooner or later it will need to also include all of the mare names that correspond to this user
        for(int i = 0; i < list.length; i++){
            list[i] = "Favorites";
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_save_stallion);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SaveListAdapter(list, viewModel));

        Button saveButton = view.findViewById(R.id.button_stallion_saved);

        saveButton.setOnClickListener(button -> {
            saveButton.setEnabled(false);
            saveButton.setText("Saving...");
            viewModel.insertSavedStallion(userViewModel.getCurrentUser().email, stallion.name);
        });

        viewModel.getStallionIsSaved().observe(getViewLifecycleOwner(), saved -> {
            if(saved){
                saveButton.setText("Save");
                saveButton.setEnabled(true);
                viewModel.setDisableSaveButton(false);
                viewModel.setStallionIsSaved(false);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .remove(this)
                        .commit();
            }
        });

        //this will handle what will happen if the users want to go back to the criteria that they had selected
        Fragment fragment = this;
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                viewModel.setDisableSaveButton(false);
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .remove(fragment)
                        .commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);
    }
}
