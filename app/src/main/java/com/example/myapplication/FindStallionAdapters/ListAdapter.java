package com.example.myapplication.FindStallionAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Stallion;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.StallionViewModel;
import com.qtalk.recyclerviewfastscroller.RecyclerViewFastScroller;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements RecyclerViewFastScroller.OnPopupTextUpdate {

    String[] nameList;
    StallionViewModel viewModel;
    String typeOfList;
    public ListAdapter(String[] list, StallionViewModel viewModel, String typeOfList){
        nameList = list;
        this.viewModel = viewModel;
        this.typeOfList = typeOfList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_stallion_recycler_view_string_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView text = holder.itemView.findViewById(R.id.text_view_attributes);
        text.setText(nameList[position]);
        CheckBox include = holder.itemView.findViewById(R.id.check_box_add_attribute);
        CheckBox disclude = holder.itemView.findViewById(R.id.check_box_disclude_attribute);

        //this will check whether the text boxes were checked or unchecked and add them to the query list in viewmodel

        include.setOnClickListener( box -> {
            if(include.isChecked()){//add to the list
                viewModel.addIncludeForQueryString(nameList[position],typeOfList );
            } else {//take from the list
                viewModel.removeIncludeForQueryString(nameList[position], typeOfList );
            }
        });

        disclude.setOnClickListener( box -> {
            if(disclude.isChecked()){//add to the list
                viewModel.addDiscludeForQueryString(nameList[position], typeOfList);
            } else {//take from the list
                viewModel.removeDiscludeForQueryString(nameList[position], typeOfList);
            }
        });



//        box.setOnClickListener(check -> {
//            if(box.isChecked()){// add to query string
//
//                viewModel.updateQueryString();
//
//            } else {//take from query string
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return nameList.length;
    }

    //this one is used to display where you are in the scroll view. it will always display the one that is at the top of the recycle view
    @NonNull
    @Override
    public CharSequence onChange(int i) {
        if(nameList[i] != null) {
            return nameList[i].substring(0, 1);//this should take the first letter from each word
        }
        return "NA";
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
