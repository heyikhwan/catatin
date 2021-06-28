package com.ikhwan.catatin.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ikhwan.catatin.Model.User;

import java.util.HashMap;

/**
 * Created by ${user} on 25/04/2018.
 */
public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context _context;

    public static final String IS_LOGGED_IN = "isLogginIn";
    public static final String ID_USER = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String FULLNAME = "fullnanme";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String GENDER = "gender";

    public Context get_context() {return _context;}

    //constructor
    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(User user)
    {
        editor.putBoolean(IS_LOGGED_IN,true);
        editor.putString(ID_USER,user.getId());
        editor.putString(USERNAME,user.getUsername());
        editor.putString(PASSWORD,user.getPassword());
        editor.putString(FULLNAME,user.getFullname());
        editor.putString(EMAIL,user.getEmail());
        editor.putString(ADDRESS,user.getAddress());
        editor.putString(GENDER,user.getGender());
        editor.commit();
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(ID_USER,sharedPreferences.getString(ID_USER,null));
        user.put(USERNAME,sharedPreferences.getString(USERNAME,null));
        user.put(PASSWORD,sharedPreferences.getString(PASSWORD,null));
        user.put(FULLNAME,sharedPreferences.getString(FULLNAME,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(ADDRESS,sharedPreferences.getString(ADDRESS,null));
        user.put(GENDER,sharedPreferences.getString(GENDER,null));
        return  user;

    }

    public void logoutUser(){
        //clearing all data from shared Preferences
        editor.clear();
        editor.commit();
    }

    public boolean isLogginIN(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }

}