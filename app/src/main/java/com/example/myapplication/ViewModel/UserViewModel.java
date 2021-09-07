package com.example.myapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.Database.MainDatabase;
import com.example.myapplication.Model.User;

public class UserViewModel extends AndroidViewModel {
    private MainDatabase database;
    private MutableLiveData<Boolean> ReadyToMoveOnSignUp = new MutableLiveData<>();
    private MutableLiveData<Boolean> ReadyToMoveOnSignIn = new MutableLiveData<>();
    private boolean userIsUnique;
    private boolean userExists;
    private User currentUser;


    public UserViewModel(@NonNull Application application) {
        super(application);
        database = Room.databaseBuilder(application, MainDatabase.class, "database1.4").allowMainThreadQueries().build();
        ReadyToMoveOnSignIn.setValue(false);
        ReadyToMoveOnSignUp.setValue(false);
        userIsUnique = false;
        userExists = false;
    }

    public void checkNewUser(String name, String email, String password){
        ReadyToMoveOnSignUp.setValue(true);
        new Thread(() -> {
            String[] emails = database.getUserDao().checkEmail(email);
            if(emails.length < 1){
                User user = new User();
                user.name = name;
                user.password = password;
                user.email = email;
                database.getUserDao().insert(user);
                setCurrentUser(user);
                userIsUnique = true;
            }
            ReadyToMoveOnSignUp.postValue(false);
        }).start();
    }

    public void checkUser(String email, String password){
        setReadyToMoveOnSignIn(true);
        new Thread(() -> {
            User[] users = database.getUserDao().checkEmailAndPassword(email, password);
            if(users.length == 1){//this means that username and password are valid
                setCurrentUser(users[0]);
                userExists = true;
            }
            ReadyToMoveOnSignIn.postValue(false);
        }).start();
    }

    public boolean isValidEmail(String email){
        for(int i = 0; i < email.length(); i++){
            if(email.substring(i, i + 1).equals("@")){
                return true;
            }
        }
        return false;
    }

    public MutableLiveData<Boolean> getReadyToMoveOnSignUp() {
        return ReadyToMoveOnSignUp;
    }

    public void setReadyToMoveOnSignUp(Boolean value) {
        ReadyToMoveOnSignUp.setValue(value);
    }

    public boolean getUserIsUnique() {
        return userIsUnique;
    }

    public void setUserIsUnique(boolean userIsUnique) {
        this.userIsUnique = userIsUnique;
    }


    public boolean getUserExists() {
        return userExists;
    }

    public void setUserExists(boolean userExists) {
        this.userExists = userExists;
    }

    public MutableLiveData<Boolean> getReadyToMoveOnSignIn() {
        return ReadyToMoveOnSignIn;
    }

    public void setReadyToMoveOnSignIn(Boolean value) {
        ReadyToMoveOnSignIn.setValue(value);
    }

    public boolean checkPassword(String password){
        if(password.length() < 7){//password must be at least 7 characters
            return false;
        }
        return true;
    }
    public User getCurrentUser() { return currentUser; }

    public void setCurrentUser(User currentUser) { this.currentUser = currentUser; }



}
