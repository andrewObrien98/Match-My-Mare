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
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.StallionViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPagerTabLayoutDisplaySubjects extends Fragment {

    ViewPager2 myViewPager2;
    ViewPagerFragmentAdapter myAdapter;
    ArrayList<String> criteria;


    public ViewPagerTabLayoutDisplaySubjects(){
        super(R.layout.find_stallion_view_pager_tab_layout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StallionViewModel viewModel = new ViewModelProvider(requireActivity()).get(StallionViewModel.class);
        myViewPager2 = view.findViewById(R.id.view_pager);

        myAdapter = new ViewPagerFragmentAdapter(requireActivity().getSupportFragmentManager(), getLifecycle());

        // add Fragments in your ViewPagerFragmentAdapter class
        viewModel.resetLists();
        criteria = viewModel.getListOfCriteria();
        for(String subject : criteria){
            myAdapter.addFragment(new ListFragment(subject));
        }

        // set Orientation in your ViewPager2
        myViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        myViewPager2.setAdapter(myAdapter);

        //name your tabs that will display the fragments
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, myViewPager2, (tab, position) -> {
            tab.setText(criteria.get(position));
        }).attach();

        //this is what happens once the user is ready to get results
        Button calculateButton = view.findViewById(R.id.button_calculate);

        calculateButton.setOnClickListener(button -> {
            button.setEnabled(false);
            viewModel.createQueryString();
        });

        viewModel.getDisplayStallions().observe(getViewLifecycleOwner(), ready -> {
            if(ready){
                if(viewModel.getStallions().length < 1){//this means that no stallions matched the criteria
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .replace(R.id.fragment_container_home, new DisplayErrorMessage(), null)
                            .commit();
                } else {//this means that stallions matched the criteria
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .replace(R.id.fragment_container_home, new DisplayAmountOfMatches(), null)
                            .commit();
                }
                viewModel.setDisplayStallions(false);
                calculateButton.setEnabled(true);
            }
        });

        //this will handle what will happen if the users want to go back to the criteria that they had selected
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_home, new SelectCriteria(), null)
                        .commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);
    }
}
