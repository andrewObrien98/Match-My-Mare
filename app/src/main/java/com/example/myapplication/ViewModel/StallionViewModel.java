package com.example.myapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.myapplication.Database.MainDatabase;
import com.example.myapplication.Model.Stallion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class StallionViewModel extends AndroidViewModel {

    MainDatabase database;
    public StallionViewModel(@NonNull Application application) {
        super(application);
        database = Room.databaseBuilder(application, MainDatabase.class, "database1.4").allowMainThreadQueries().build();
        isDatabaseReady.setValue(false);
        isListReady.setValue(false);
        displayStallions.setValue(false);
        thereIsNoList.setValue(false);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////// this is the method that calls on the CSV file and pulls the data and populates the //////
    ///////////////////////// stallion database with data that can then be accessed for the rest of the app      //////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private MutableLiveData<Boolean> isDatabaseReady = new MutableLiveData<>();
    public MutableLiveData<Boolean> getIsDatabaseReady() { return isDatabaseReady; }
    public void setIsDatabaseReady(boolean value) { this.isDatabaseReady.setValue(value); }

    //this as the name implies is where we create the database that will be used by everything for finding stallions
    public void insertStallion(InputStream is){
        isDatabaseReady.setValue(true);
        new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line;
            try {
                while (((line = reader.readLine()) != null)) {//reads through every line and pulls the data from it to create a stallion
                    String[] list = line.split(",");
                    Stallion stallion = new Stallion();
                    stallion.name = list[0];
                    stallion.sire = list[1];
                    stallion.damsire = list[2];
                    stallion.dams_damsire = list[3];
                    stallion.dob = list[4];
                    stallion.registry_primary = list[5];
                    stallion.registry_secondary = list[6];
                    stallion.height = list[7];
                    stallion.color = list[8];
                    stallion.WFFS_stat = list[9];
                    stallion.results = list[10];
                    stallion.contact = list[11];
                    stallion.location = list[12];
                    stallion.breeder = list[13];
                    database.getStallionDao().insert(stallion);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            isDatabaseReady.postValue(false);//this means that the database is ready
        }).start();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //// the following methods and variables will take the criteria that the user has selected and pull the data //////////////
    //// from the database for each of those criteria                                                            //////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //these lists will be used to hold of the data from the database for its corresponding title. These are only pulled if user selects this criteria
    private String[] nameList;
    private String[] sireList;
    private String[] damsireList;
    private String[] grandDamsireList;
    private String[] dobList;
    private String[] heightList;
    private String[] colorList;
    private String[] wffsList;
    private String[] resultsList;
    private String[] contactList;
    private String[] locationList;
    private String[] breederList;
    private String[] primaryRegistryList;
    private String[] secondaryRegistryList;
    public String[] getNameList(){return nameList;}
    public String[] getSireList(){return sireList;}
    public String[] getDamsireList() { return damsireList; }
    public String[] getGrandDamsireList() { return grandDamsireList; }
    public String[] getDobList() { return dobList; }
    public String[] getHeightList() { return heightList; }
    public String[] getColorList() { return colorList; }
    public String[] getWffsList() { return wffsList; }
    public String[] getResultsList() { return resultsList; }
    public String[] getContactList() { return contactList; }
    public String[] getLocationList() { return locationList; }
    public String[] getBreederList() { return breederList; }
    public String[] getPrimaryRegistryList() { return primaryRegistryList; }
    public String[] getSecondaryRegistryList() { return secondaryRegistryList; }

    //this is just used to keep track of the list of criteria that the user chose
    private ArrayList<String> listOfCriteria;
    public ArrayList<String> getListOfCriteria() {return listOfCriteria;}

    //this is called if the user chose criteria and we have pulled from the database all the data we need for each subject
    private MutableLiveData<Boolean> isListReady = new MutableLiveData<>();
    public MutableLiveData<Boolean> getIsListReady() { return isListReady; }
    public void setIsListReady(Boolean value) { this.isListReady.setValue(value); }

    //this is called if the user chose no criteria and means that we will just go to displaying every horse in the database
    private MutableLiveData<Boolean> thereIsNoList = new MutableLiveData<>();
    public MutableLiveData<Boolean> getThereIsNoList() { return thereIsNoList; }
    public void setThereIsNoList(Boolean value) { this.thereIsNoList.setValue(value); }

    //as the name implies. This is where we populate the lists up above with the data that corresponds to it
    public void setUpSubjectLists(ArrayList<String> subjects){
        listOfCriteria = subjects;
        new Thread(() -> {
            for(String subject: subjects){
                if(subject.equals("Name")){
                    nameList = database.getStallionDao().getNameList();
                }
                if(subject.equals("Sire")){
                    sireList = database.getStallionDao().getSireList();
                }
                if(subject.equals("Damsire")){
                    damsireList = database.getStallionDao().getDamsireList();
                }
                if(subject.equals("Grand Damsire")){
                    grandDamsireList = database.getStallionDao().getGrandDamsireList();
                }
                if(subject.equals("Date of Birth")){
                    dobList = database.getStallionDao().getDOBList();
                }
                if(subject.equals("Height")){
                    heightList = database.getStallionDao().getHeightList();
                }
                if(subject.equals("Color")){
                    colorList = database.getStallionDao().getColorList();
                }
                if(subject.equals("Primary Registry")){
                    primaryRegistryList = database.getStallionDao().getRegistryPrimaryList();
                }
                if(subject.equals("Secondary Registry")){
                    secondaryRegistryList = database.getStallionDao().getRegistrySecondaryList();
                }
                if(subject.equals("Results")){
                    resultsList = database.getStallionDao().getResultsList();
                }
                if(subject.equals("WFFS Status")){
                    wffsList = database.getStallionDao().getWFFSList();
                }
                if(subject.equals("Contact")){
                    contactList = database.getStallionDao().getContactList();
                }
                if(subject.equals("Location")){
                    locationList = database.getStallionDao().getLocationList();
                }
                if(subject.equals("Breeder")){
                    breederList = database.getStallionDao().getBreederList();
                }
            }
            if(subjects.size() < 1){//this means that no criteria was chosen so we will just display all of the horses
                stallions = database.getStallionDao().getAllStallions();
                thereIsNoList.postValue(true);
            } else {
                isListReady.postValue(true);
            }
        }).start();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////// this method will query the database based on criteria that the user has given it ////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //this will always be the current list of stallion based on what the user has checkmarked and chosen
    private Stallion[] stallions;
    public Stallion[] getStallions(){ return stallions; }

    //this will let us know once stallion has been populated and that we are good to display the stallion list now
    private MutableLiveData<Boolean> displayStallions = new MutableLiveData<>();
    public MutableLiveData<Boolean> getDisplayStallions(){ return displayStallions; }
    public void setDisplayStallions(Boolean value){ displayStallions.setValue(value); }

    //this is where we get the data that populates the stallion list
    public void queryDatabase(){
        new Thread(() -> {
            if(!queryString.equals("SELECT * FROM stallion WHERE")){//this means that the user chose criteria
                SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString, args.toArray());
                stallions = database.getStallionDao().getSpecificStallion(query);
            } else {//we just get all of the stallions if the user chose no criteria
                stallions = database.getStallionDao().getAllStallions();
            }
            //we now reset everything for the next time
            queryString = "SELECT * FROM stallion WHERE";
            args = new ArrayList<>();
            //we are now good to display everything
            displayStallions.postValue(true);
        }).start();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////// These next two methods are used to create the query String that pulls ///////////////////////////////
    /////////// the stallions based on what the user check marked                     ///////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private String queryString = "SELECT * FROM stallion WHERE";
    private List<Object> args = new ArrayList<>();
    private boolean conditionsMet = false;

    //this is where we create the query string that is used in the method queryDatabase
    public void createQueryString(){
        ArrayList<String> list = new ArrayList<>();
        list.add(createQuerySubString(nameIncludeList, nameDiscludeList, "name"));
        list.add(createQuerySubString(sireIncludeList, sireDiscludeList, "sire"));
        list.add(createQuerySubString(damsireIncludeList, damsireDiscludeList, "damsire"));
        list.add(createQuerySubString(grandIncludeList, grandDiscludeList, "dams_damsire"));
        list.add(createQuerySubString(dobIncludeList, dobDiscludeList, "dob"));
        list.add(createQuerySubString(heightIncludeList, heightDiscludeList, "height"));
        list.add(createQuerySubString(colorIncludeList, colorDiscludeList, "color"));
        list.add(createQuerySubString(wffsIncludeList, wffsDiscludeList, "WFFS_stat"));
        list.add(createQuerySubString(resultsIncludeList, resultsDiscludeList, "results"));
        list.add(createQuerySubString(primaryIncludeList, primaryDiscludeList, "registry_primary"));
        list.add(createQuerySubString(secondaryIncludeList, secondaryDiscludeList, "registry_secondary"));
        list.add(createQuerySubString(contactIncludeList, contactDiscludeList, "contact"));
        list.add(createQuerySubString(locationIncludeList, locationDiscludeList, "location"));
        list.add(createQuerySubString(breederIncludeList, breederDiscludeList, "breeder"));

        conditionsMet = false;//this is to track whether its the first one so we dont do FROM.... stallion WHERE AND ...
        for(int i = 0; i < list.size(); i++){
            if(conditionsMet && !list.get(i).equals("")){//we now include AND
                queryString += " AND";
            }
            if(!list.get(i).equals("")){// this just makes sure as well that the list is just empty
                queryString += " " + list.get(i);
                if(!conditionsMet){
                    conditionsMet = true;
                }
            }
        }
        queryDatabase();
    }

    //this is what populates the lists for the method createQueryString
    // this will make the individual sub string for each subject so then only AND needs to be inserted up above
    // if list has anything it will populate with statements like (name = ? OR name = ?) or it might do (NOT sire = ? AND NOT sire = ?)
    public String createQuerySubString(ArrayList<String> include, ArrayList<String> disclude, String type){
        String list = "";
        if(include.size() != 0 || disclude.size() != 0){// for the include list
            list += "(";
            for(int i = 0; i < include.size(); i++){
                if(i == 0){// this means that its the first one so AND or OR doesnt come before it
                    list += type + " = ?";
                } else { // this means its not the first one
                    list += " " + type + " = ?";
                }
                args.add(include.get(i));//we then add the actual argument that will replace the ?
                if(i != include.size() - 1){// We include OR after it unless its the last one, We use OR for what we want
                    list += " OR";
                } else if(i == include.size() - 1 && disclude.size() != 0){// we use AND to connect the include and disclude for the same subject althought it really makes no sense, But I do it to track user error
                    list += " AND";
                }
            }
            for(int i = 0; i < disclude.size(); i++){//this is for the disclude list
                list += " NOT " + type + " = ?";
                args.add(disclude.get(i));//add the actual argument that will replace the ?
                if(i != disclude.size() - 1){//makes sure that its not the last one
                    list += " AND";
                }
            }
            list += ")";
        }
        return list;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //All the methods from here down are used to populate the Lists that will be used to create the query String. ////////////////
    // These methods are called from List adapter during each pass of going through the check box ////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //these list will contain String list of how the user wants to filter it. these will all eventually be add to the args list.
    private ArrayList<String> nameIncludeList = new ArrayList<>();
    private ArrayList<String> nameDiscludeList = new ArrayList<>();
    private ArrayList<String> sireIncludeList = new ArrayList<>();
    private ArrayList<String> sireDiscludeList = new ArrayList<>();
    private ArrayList<String> damsireIncludeList = new ArrayList<>();
    private ArrayList<String> damsireDiscludeList = new ArrayList<>();
    private ArrayList<String> grandIncludeList = new ArrayList<>();
    private ArrayList<String> grandDiscludeList = new ArrayList<>();
    private ArrayList<String> dobIncludeList = new ArrayList<>();
    private ArrayList<String> dobDiscludeList = new ArrayList<>();
    private ArrayList<String> primaryIncludeList = new ArrayList<>();
    private ArrayList<String> primaryDiscludeList = new ArrayList<>();
    private ArrayList<String> secondaryIncludeList = new ArrayList<>();
    private ArrayList<String> secondaryDiscludeList = new ArrayList<>();
    private ArrayList<String> resultsIncludeList = new ArrayList<>();
    private ArrayList<String> resultsDiscludeList = new ArrayList<>();
    private ArrayList<String> wffsIncludeList = new ArrayList<>();
    private ArrayList<String> wffsDiscludeList = new ArrayList<>();
    private ArrayList<String> locationIncludeList = new ArrayList<>();
    private ArrayList<String> locationDiscludeList = new ArrayList<>();
    private ArrayList<String> contactIncludeList = new ArrayList<>();
    private ArrayList<String> contactDiscludeList = new ArrayList<>();
    private ArrayList<String> breederIncludeList = new ArrayList<>();
    private ArrayList<String> breederDiscludeList = new ArrayList<>();
    private ArrayList<String> heightIncludeList = new ArrayList<>();
    private ArrayList<String> heightDiscludeList = new ArrayList<>();
    private ArrayList<String> colorIncludeList = new ArrayList<>();
    private ArrayList<String> colorDiscludeList = new ArrayList<>();

    //everytime we go back to ViewPagerTabLayoutDisplaySubjects class we need to reset the lists up above
    public void resetLists(){
        nameIncludeList = new ArrayList<>();
        nameDiscludeList = new ArrayList<>();
        sireIncludeList = new ArrayList<>();
        sireDiscludeList = new ArrayList<>();
        damsireIncludeList = new ArrayList<>();
        damsireDiscludeList = new ArrayList<>();
        grandIncludeList = new ArrayList<>();
        grandDiscludeList = new ArrayList<>();
        dobIncludeList = new ArrayList<>();
        dobDiscludeList = new ArrayList<>();
        primaryIncludeList = new ArrayList<>();
        primaryDiscludeList = new ArrayList<>();
        secondaryIncludeList = new ArrayList<>();
        secondaryDiscludeList = new ArrayList<>();
        resultsIncludeList = new ArrayList<>();
        resultsDiscludeList = new ArrayList<>();
        wffsIncludeList = new ArrayList<>();
        wffsDiscludeList = new ArrayList<>();
        locationIncludeList = new ArrayList<>();
        locationDiscludeList = new ArrayList<>();
        contactIncludeList = new ArrayList<>();
        contactDiscludeList = new ArrayList<>();
        breederIncludeList = new ArrayList<>();
        breederDiscludeList = new ArrayList<>();
        heightIncludeList = new ArrayList<>();
        heightDiscludeList = new ArrayList<>();
        colorIncludeList = new ArrayList<>();
        colorDiscludeList = new ArrayList<>();
    }

    //this will add a disclude that the user wants to use
    public void addDiscludeForQueryString(String word, String type){
        if(type.equals("name")){
            nameDiscludeList.add(word);
        }
        if(type.equals("sire")){
            sireDiscludeList.add(word);
        }
        if(type.equals("damsire")){
            damsireDiscludeList.add(word);
        }
        if(type.equals("dams_damsire")){
            grandDiscludeList.add(word);
        }
        if(type.equals("dob")){
            dobDiscludeList.add(word);
        }
        if(type.equals("height")){
            heightDiscludeList.add(word);
        }
        if(type.equals("color")){
            colorDiscludeList.add(word);
        }
        if(type.equals("WFFS_stat")){
            wffsDiscludeList.add(word);
        }
        if(type.equals("results")){
            resultsDiscludeList.add(word);
        }
        if(type.equals("registry_primary")){
            primaryDiscludeList.add(word);
        }
        if(type.equals("registry_secondary")){
            secondaryDiscludeList.add(word);
        }
        if(type.equals("contact")){
            contactDiscludeList.add(word);
        }
        if(type.equals("location")){
            locationDiscludeList.add(word);
        }
        if(type.equals("breeder")){
            breederDiscludeList.add(word);
        }
    }

    //this will add what the user wants to include
    public void addIncludeForQueryString(String word, String type){
        if(type.equals("name")){
            nameIncludeList.add(word);
        }
        if(type.equals("sire")){
            sireIncludeList.add(word);
        }
        if(type.equals("damsire")){
            damsireIncludeList.add(word);
        }
        if(type.equals("dams_damsire")){
            grandIncludeList.add(word);
        }
        if(type.equals("dob")){
            dobIncludeList.add(word);
        }
        if(type.equals("height")){
            heightIncludeList.add(word);
        }
        if(type.equals("color")){
            colorIncludeList.add(word);
        }
        if(type.equals("WFFS_stat")){
            wffsIncludeList.add(word);
        }
        if(type.equals("results")){
            resultsIncludeList.add(word);
        }
        if(type.equals("registry_primary")){
            primaryIncludeList.add(word);
        }
        if(type.equals("registry_secondary")){
            secondaryIncludeList.add(word);
        }
        if(type.equals("contact")){
            contactIncludeList.add(word);
        }
        if(type.equals("location")){
            locationIncludeList.add(word);
        }
        if(type.equals("breeder")){
            breederIncludeList.add(word);
        }
    }

     //this is here so that if a user clicked on something and then unclicked it that it gets taken out
    public void removeDiscludeForQueryString(String word, String type){
        if(type.equals("name")){
            nameDiscludeList.remove(word);
        }
        if(type.equals("sire")){
            sireDiscludeList.remove(word);
        }
        if(type.equals("damsire")){
            damsireDiscludeList.remove(word);
        }
        if(type.equals("dams_damsire")){
            grandDiscludeList.remove(word);
        }
        if(type.equals("dob")){
            dobDiscludeList.remove(word);
        }
        if(type.equals("height")){
            heightDiscludeList.remove(word);
        }
        if(type.equals("color")){
            colorDiscludeList.remove(word);
        }
        if(type.equals("WFFS_stat")){
            wffsDiscludeList.remove(word);
        }
        if(type.equals("results")){
            resultsDiscludeList.remove(word);
        }
        if(type.equals("registry_primary")){
            primaryDiscludeList.remove(word);
        }
        if(type.equals("registry_secondary")){
            secondaryDiscludeList.remove(word);
        }
        if(type.equals("contact")){
            contactDiscludeList.remove(word);
        }
        if(type.equals("location")){
            locationDiscludeList.remove(word);
        }
        if(type.equals("breeder")){
            breederDiscludeList.remove(word);
        }
    }

    //this is here so that if a user originally planned on including something and clicked it and then unclicked it that it gets removed from the list
    public void removeIncludeForQueryString(String word, String type){
        if(type.equals("name")){
            nameIncludeList.remove(word);
        }
        if(type.equals("sire")){
            sireIncludeList.remove(word);
        }
        if(type.equals("damsire")){
            damsireIncludeList.remove(word);
        }
        if(type.equals("dams_damsire")){
            grandIncludeList.remove(word);
        }
        if(type.equals("dob")){
            dobIncludeList.remove(word);
        }
        if(type.equals("height")){
            heightIncludeList.remove(word);
        }
        if(type.equals("color")){
            colorIncludeList.remove(word);
        }
        if(type.equals("WFFS_stat")){
            wffsIncludeList.remove(word);
        }
        if(type.equals("results")){
            resultsIncludeList.remove(word);
        }
        if(type.equals("registry_primary")){
            primaryIncludeList.remove(word);
        }
        if(type.equals("registry_secondary")){
            secondaryIncludeList.remove(word);
        }
        if(type.equals("contact")){
            contactIncludeList.remove(word);
        }
        if(type.equals("location")){
            locationIncludeList.remove(word);
        }
        if(type.equals("breeder")){
            breederIncludeList.remove(word);
        }
    }
}
