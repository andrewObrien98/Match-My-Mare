package com.example.myapplication.FindStallionFragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.FindStallionAdapters.ListAdapter;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.StallionViewModel;

import java.util.Objects;

public class ListFragment extends Fragment {//this class was intended to display all of the options for all the columns like name, sire, color, height.
    String typeOfList;
    String[] list = new String[1];
    public ListFragment(String typeOfList){
        super(R.layout.find_stallion_fragment_string_list);
        this.typeOfList = typeOfList;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StallionViewModel viewModel = new ViewModelProvider(requireActivity()).get(StallionViewModel.class);
        //this is where I will set the name of type of list it will pull up
        TextView title = view.findViewById(R.id.text_subject_name);
        title.setText(typeOfList);

        //telling the viewModel what type of data to pull from the database
        if(typeOfList.equals("Name")){
            list = viewModel.getNameList();
            typeOfList = "name";
        }
        if(typeOfList.equals("Sire")){
            list = viewModel.getSireList();
            typeOfList = "sire";
        }
        if(typeOfList.equals("Damsire")){
            list = viewModel.getDamsireList();
            typeOfList = "damsire";
        }
        if(typeOfList.equals("Grand Damsire")){
            list = viewModel.getGrandDamsireList();
            typeOfList = "dams_damsire";
        }
        if(typeOfList.equals("Date of Birth")){
            list = viewModel.getDobList();
            typeOfList = "dob";
        }
        if(typeOfList.equals("Height")){
            list = viewModel.getHeightList();
            typeOfList = "height";
        }
        if(typeOfList.equals("Color")){
            list = viewModel.getColorList();
            typeOfList = "color";
        }
        if(typeOfList.equals("WFFS Status")){
            list = viewModel.getWffsList();
            typeOfList = "WFFS_stat";
        }
        if(typeOfList.equals("Results")){
            list = viewModel.getResultsList();
            typeOfList = "results";
        }
        if(typeOfList.equals("Primary Registry")){
            list = viewModel.getPrimaryRegistryList();
            typeOfList = "registry_primary";
        }
        if(typeOfList.equals("Secondary Registry")){
            list = viewModel.getSecondaryRegistryList();
            typeOfList = "registry_secondary";
        }
        if(typeOfList.equals("Contact")){
            list = viewModel.getContactList();
            typeOfList = "contact";
        }
        if(typeOfList.equals("Location")){
            list = viewModel.getLocationList();
            typeOfList = "location";
        }
        if(typeOfList.equals("Breeder")){
            list = viewModel.getBreederList();
            typeOfList = "breeder";
        }
        RecyclerView recyclerView = view.findViewById(R.id.name_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ListAdapter(list,viewModel, typeOfList ));
    }
}
