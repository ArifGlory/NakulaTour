package Kelas;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Glory on 19/09/2018.
 */

public class UserPreference {
    private String KEY_BAGIAN = "bagian";
    private String KEY_EMAIL = "email";
    private String KEY_TOKEN = "token";
    private String KEY_ID_USER = "username";
    private String KEY_IS_LOGGED_IN = "islogin";
    private SharedPreferences preferences;

    public UserPreference(Context context){
        String PREFS_NAME = "UserPref";
        preferences = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
    }

    public void setBagian(String bagian){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_BAGIAN,bagian);
        editor.apply();
    }

    public void setEmail(String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_EMAIL,email);
        editor.apply();
    }

    public void setToken(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_TOKEN,token);
        editor.apply();
    }

    public void setIdUser(String idUser){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_ID_USER,idUser);
        editor.apply();
    }

    public void setIsLoggedIn(String isLoggedIn){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_IS_LOGGED_IN,isLoggedIn);
        editor.apply();
    }

    public String getIsLoggedIn(){
        return preferences.getString(KEY_IS_LOGGED_IN,null);
    }

    public String getBagian(){
        return preferences.getString(KEY_BAGIAN,null);
    }

    public String getEmail(){
        return preferences.getString(KEY_EMAIL,null);
    }

    public String getIdUser(){
        return preferences.getString(KEY_ID_USER,null);
    }

    public String getToken(){
        return preferences.getString(KEY_TOKEN,null);
    }
}
