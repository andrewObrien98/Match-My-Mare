package com.example.myapplication.FindStallionFragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Model.Stallion;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.SaveStallionViewModel;

import java.util.ArrayList;

public class StallionFragment extends Fragment {
    Stallion stallion;
    StallionFragment(Stallion stallion){
        super(R.layout.display_stallion_format);
        this.stallion = stallion;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SaveStallionViewModel viewModel = new ViewModelProvider(getActivity()).get(SaveStallionViewModel.class);
        TextView name = view.findViewById(R.id.text_display_name);
        TextView sire = view.findViewById(R.id.text_display_sire);
        TextView damsire = view.findViewById(R.id.text_display_damsire);
        TextView grand = view.findViewById(R.id.text_display_grand);
        TextView dob = view.findViewById(R.id.text_display_dob);
        TextView color = view.findViewById(R.id.text_display_color);
        TextView height = view.findViewById(R.id.text_display_height);
        TextView primary = view.findViewById(R.id.text_display_primary);
        TextView secondary = view.findViewById(R.id.text_display_secondary);
        TextView wffs = view.findViewById(R.id.text_display_wffs);
        TextView results = view.findViewById(R.id.text_display_results);
        TextView location = view.findViewById(R.id.text_display_location);
        TextView contact = view.findViewById(R.id.text_display_contact);
        TextView breeder = view.findViewById(R.id.text_display_breeder);

        name.setText(stallion.name);
        sire.setText(stallion.sire);
        damsire.setText(stallion.damsire);
        grand.setText(stallion.dams_damsire);
        dob.setText(stallion.dob);
        color.setText(stallion.color);
        height.setText(stallion.height);
        primary.setText(stallion.registry_primary);
        secondary.setText(stallion.registry_secondary);
        wffs.setText(stallion.WFFS_stat);
        results.setText(stallion.results);
        location.setText(stallion.location);
        contact.setText(stallion.contact);
        breeder.setText(stallion.breeder);

        //if the save button is clicked
        Button saveButton = view.findViewById(R.id.button_save_stallion);

        saveButton.setOnClickListener(button -> {
            viewModel.setSelectedSaveCriteria(new ArrayList<>());
            viewModel.setDisableSaveButton(true);
            button.setEnabled(false);//make sure to set this back to true
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .add(R.id.fragment_container_home, new DisplaySaveBox(stallion), null)
                    .commit();
        });


        //the next things may seem redundant and confusing but basically it is here to help with how the save button looks and///
        //so that users will not be able to click it when on the save fragment and so that the button stays saying saved//////
        //The reason its so weird is that each time you flip to the right or flip away from something a distance of like 2 fragments//
        ///then I think it gets recalled so you have to have it set up with a viewmodel Observe and so that it also happens once called//

        //this way if we are looking at saving screen you cant continue to press the save button elsewhere for fragments that havent been viewed yet
        if(viewModel.getDisableSaveButtonBoolean()){
            saveButton.setEnabled(false);
        }
        //if you are part of the list then basically this will make it so that you appear to have been saved already
        if(viewModel.getCurrentSavedStallions().contains(stallion.name)){
            saveButton.setEnabled(true);
            saveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            saveButton.setTextColor(getResources().getColor(R.color.white));
            saveButton.setText("Saved");
        }

        //this way if we are looking at saving screen you cant continue to press the save button elsewhere for fragments that have been viewed yet
        viewModel.getDisableSaveButton().observe(getViewLifecycleOwner(), disable -> {
            if(disable){
                saveButton.setEnabled(false);
            } else {
                saveButton.setEnabled(true);
            }
        });

        viewModel.getStallionIsSaved().observe(getViewLifecycleOwner(), saved -> {
            if(saved) {
                saveButton.setEnabled(true);
                saveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                saveButton.setTextColor(getResources().getColor(R.color.white));
                saveButton.setText("Saved");
            } else if (!viewModel.getCurrentSavedStallions().contains(stallion.name)){//any fragment that is still registering and not in the list will not just look ordinary
                saveButton.setBackgroundColor(getResources().getColor(R.color.white));
                saveButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                saveButton.setText("Save");
            }
        });
        //create something that is listening and once the stallion is saved to its right place it will then say saved and fill in the box
    }
}
