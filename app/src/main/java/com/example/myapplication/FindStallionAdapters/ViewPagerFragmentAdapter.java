package com.example.myapplication.FindStallionAdapters;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> arrayList = new ArrayList<>();

    public ViewPagerFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    public void addFragment(Fragment fragment) {
        arrayList.add(fragment);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return arrayList.get(position);
    }
}
