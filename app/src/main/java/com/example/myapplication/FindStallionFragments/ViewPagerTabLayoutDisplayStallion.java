package com.example.myapplication.FindStallionFragments;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.FindStallionAdapters.ViewPagerFragmentAdapter;
import com.example.myapplication.Model.Stallion;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.SaveStallionViewModel;
import com.example.myapplication.ViewModel.StallionViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPagerTabLayoutDisplayStallion extends Fragment {

    ViewPager2 myViewPager2;
    ViewPagerFragmentAdapter myAdapter;


    public ViewPagerTabLayoutDisplayStallion(){
        super(R.layout.display_stallion_view_pager_tab_layout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StallionViewModel viewModel = new ViewModelProvider(requireActivity()).get(StallionViewModel.class);
        SaveStallionViewModel saveStallionViewModel = new ViewModelProvider(requireActivity()).get(SaveStallionViewModel.class);
        saveStallionViewModel.setCurrentSavedStallions(new ArrayList<>());//this has to do with saving stallions

        myViewPager2 = view.findViewById(R.id.view_pager_display_stallion);

        myAdapter = new ViewPagerFragmentAdapter(requireActivity().getSupportFragmentManager(), getLifecycle());

        Stallion[] stallions = viewModel.getStallions();

        // add Fragments in your ViewPagerFragmentAdapter class
        for(Stallion stallion: stallions){
            myAdapter.addFragment(new StallionFragment(stallion));
        }

        // set Orientation in your ViewPager2
        myViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        myViewPager2.setAdapter(myAdapter);

        //name your tabs that will display the fragments
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_display_stallion);
        new TabLayoutMediator(tabLayout, myViewPager2, (tab, position) -> {
            tab.setText("");
        }).attach();


        //this will handle what will happen if the users want to go back to the criteria that they had selected
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(viewModel.getListOfCriteria().size() < 1){//this means that no criteria was selected. so go back to beggining
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container_home, new MainFragment(), null)
                            .commit();
                } else {//criteria was selected so go back to that point
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container_home, new ViewPagerTabLayoutDisplaySubjects(), null)
                            .commit();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);
    }


}