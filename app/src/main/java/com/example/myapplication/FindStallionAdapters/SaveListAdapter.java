package com.example.myapplication.FindStallionAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ViewModel.SaveStallionViewModel;

public class SaveListAdapter extends RecyclerView.Adapter<SaveListAdapter.ViewHolder> {

    String[] listOfSavingOptions;
    SaveStallionViewModel viewModel;
    public SaveListAdapter(String[] listOfSavingOptions, SaveStallionViewModel viewModel){
        this.listOfSavingOptions = listOfSavingOptions;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_save_options_recycle_view, parent, false);
        return new SaveListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckBox box = holder.itemView.findViewById(R.id.check_box_save_options);
        box.setText(listOfSavingOptions[position]);

        box.setOnClickListener(view -> {
            if(box.isChecked()){
                viewModel.addToSelectedSaveCriteriaList(listOfSavingOptions[position]);
            } else {
                viewModel.removeItemFromSelectedSaveCriteriaList(listOfSavingOptions[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfSavingOptions.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
