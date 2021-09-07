package com.example.myapplication.FindStallionFragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.ViewModel.StallionViewModel;

import java.io.InputStream;
import java.util.ArrayList;

public class SelectCriteria extends Fragment {
    SelectCriteria(){
        super(R.layout.find_stallion_select_criteria);
    }

    boolean isDatabaseReady = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StallionViewModel viewModel = new ViewModelProvider(getActivity()).get(StallionViewModel.class);

        //this will get the stallion database ready
        InputStream is = getResources().openRawResource(R.raw.stallion_info2);
        viewModel.insertStallion(is);
        Button continueButton = view.findViewById(R.id.button_continue);
        viewModel.getIsDatabaseReady().observe(getViewLifecycleOwner(), ready -> {
            if(ready && ! isDatabaseReady){
                continueButton.setEnabled(false);
                isDatabaseReady = ready;
            }
            if(!ready && isDatabaseReady){
                continueButton.setEnabled(true);
                isDatabaseReady = ready;
            }
        });

        //this next part will collect the criteria that they have chosen
        continueButton.setOnClickListener(button -> {
            button.setEnabled(false);
            ArrayList<String> criteria = new ArrayList<>();

            CheckBox checkBox = view.findViewById(R.id.check_box_name);
            if(checkBox.isChecked()){
                criteria.add("Name");
            }
            checkBox = view.findViewById(R.id.check_box_sire);
            if(checkBox.isChecked()){
                criteria.add("Sire");
            }
            checkBox = view.findViewById(R.id.check_box_damsire);
            if(checkBox.isChecked()){
                criteria.add("Damsire");
            }
            checkBox = view.findViewById(R.id.check_box_grand_damsire);
            if(checkBox.isChecked()){
                criteria.add("Grand Damsire");
            }
            checkBox = view.findViewById(R.id.check_box_dob);
            if(checkBox.isChecked()){
                criteria.add("Date of Birth");
            }
            checkBox = view.findViewById(R.id.check_box_height);
            if(checkBox.isChecked()){
                criteria.add("Height");
            }
            checkBox = view.findViewById(R.id.check_box_color);
            if(checkBox.isChecked()){
                criteria.add("Color");
            }
            checkBox = view.findViewById(R.id.check_box_primary_registry);
            if(checkBox.isChecked()){
                criteria.add("Primary Registry");
            }
            checkBox = view.findViewById(R.id.check_box_secondary_registry);
            if(checkBox.isChecked()){
                criteria.add("Secondary Registry");
            }
            checkBox = view.findViewById(R.id.check_box_results);
            if(checkBox.isChecked()){
                criteria.add("Results");
            }
            checkBox = view.findViewById(R.id.check_box_wffs_status);
            if(checkBox.isChecked()){
                criteria.add("WFFS Status");
            }
            checkBox = view.findViewById(R.id.check_box_contact);
            if(checkBox.isChecked()){
                criteria.add("Contact");
            }
            checkBox = view.findViewById(R.id.check_box_location);
            if(checkBox.isChecked()){
                criteria.add("Location");
            }
            checkBox = view.findViewById(R.id.check_box_breeder);
            if(checkBox.isChecked()){
                criteria.add("Breeder");
            }
            //i will now get the list for all of the criteria we chose
            viewModel.setUpSubjectLists(criteria);

        });

        //if the user selected criteria then we go here
        viewModel.getIsListReady().observe(getViewLifecycleOwner(), ready -> {
            continueButton.setEnabled(true);
            if(ready){
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.fragment_container_home, new ViewPagerTabLayoutDisplaySubjects(), null)
                        .commit();
                viewModel.setIsListReady(false);
            }
        });

        //if user did not select criteria then we just go straight to showing all of the horses
        viewModel.getThereIsNoList().observe(getViewLifecycleOwner(), ready -> {
            if(ready){
                continueButton.setEnabled(true);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.fragment_container_home, new ViewPagerTabLayoutDisplayStallion(), null)
                        .commit();
                viewModel.setThereIsNoList(false);
            }
        });

        //if user presses the back button here we will just go to the main fragment
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_home, new MainFragment(), null)
                        .commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);
    }
}
