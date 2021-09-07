package com.example.myapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.Database.MainDatabase;
import com.example.myapplication.Model.SavedStallion;

import java.util.ArrayList;

public class SaveStallionViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> disableSaveButton = new MutableLiveData<>();
    private ArrayList<String> selectedSaveCriteria = new ArrayList<>();
    private MutableLiveData<Boolean> stallionIsSaved = new MutableLiveData<>();
    private ArrayList<String> currentSavedStallions = new ArrayList<>();
    MainDatabase database;

    public SaveStallionViewModel(@NonNull Application application) {
        super(application);
        database = Room.databaseBuilder(application, MainDatabase.class, "database1.4").allowMainThreadQueries().build();
        disableSaveButton.setValue(false);
    }

    public void insertSavedStallion(String email, String stallionName){
        new Thread(() -> {
            if(getSelectedSaveCriteriaList()){//make sure the list is not empty
                for(String criteria: selectedSaveCriteria){
                    SavedStallion stallion = new SavedStallion();
                    stallion.username = email;
                    stallion.stallionName = stallionName;
                    stallion.criteria = criteria;
                    if(database.getSavedStallionDao().checkSavedStallion(stallionName, email, criteria).length < 1){//this make sure it doesnt already exist
                        currentSavedStallions.add(stallionName);
                        database.getSavedStallionDao().insert(stallion);
                    }
                }
            }
            stallionIsSaved.postValue(true);
        }).start();
    }

    public boolean getDisableSaveButtonBoolean() {
        return disableSaveButton.getValue();
    }

    public MutableLiveData<Boolean> getDisableSaveButton() {return disableSaveButton;}

    public void setDisableSaveButton(Boolean value) {
        this.disableSaveButton.setValue(value);
    }

    public void setSelectedSaveCriteria(ArrayList<String> list){
        selectedSaveCriteria = list;
    }

    public void addToSelectedSaveCriteriaList(String criteria){
        selectedSaveCriteria.add(criteria);
    }
    public void removeItemFromSelectedSaveCriteriaList(String criteria){
        selectedSaveCriteria.remove(criteria);
    }
    public boolean getSelectedSaveCriteriaList(){
        return selectedSaveCriteria.size() > 0;
    }
    public MutableLiveData<Boolean> getStallionIsSaved() {
        return stallionIsSaved;
    }
    public void setStallionIsSaved(Boolean value) {
        this.stallionIsSaved.setValue(value);
    }
    public ArrayList<String> getCurrentSavedStallions() {
        return currentSavedStallions;
    }

    public void setCurrentSavedStallions(ArrayList<String> currentSavedStallions) {
        this.currentSavedStallions = currentSavedStallions;
    }


}
