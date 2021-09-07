package com.example.myapplication.FindStallionFragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.myapplication.LoginFragments.LoginMainScreen;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainFragment extends Fragment {

    public MainFragment(){
        super(R.layout.main_fragment_container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //this is where we will always go unless we click on something else
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_home, new SelectCriteria(), null)
                .commit();

        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        MaterialToolbar toolbar = view.findViewById(R.id.topAppBar);
        NavigationView navigationView = view.findViewById(R.id.navigation_view);

        toolbar.setNavigationOnClickListener(v -> {
            drawerLayout.open();
        });

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            if(menuItem.getItemId() == R.id.stallion_finder){
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_home, new SelectCriteria(), null)
                        .commit();
            }
            if(menuItem.getItemId() == R.id.log_out){
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_login, new LoginMainScreen(), null)
                        .commit();
            }
            return true;
        });
    }
}
