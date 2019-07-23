package com.example.markable.footballapptest.Classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.markable.footballapptest.LoginActivity;

import java.util.HashMap;

public class SessionManager {

    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    public static final String PREF_NAME = "AndroidUserPref";
    private static final String KEY_LOGIN = "loggedIN";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_SETTING = "setting";

    public SessionManager(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
   //Создание настроек
    public void createSetting(String email, String password, int setting){
        editor.putBoolean(KEY_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putInt(KEY_SETTING, setting);
        editor.commit();
    }
    //проверяем залогинился ли пользователь
    public boolean checkLogin(){
        if(!this.isLoggedIn()){
            Toast.makeText(this.context,"Необходимо войти", Toast.LENGTH_LONG).show();
            return isLoggedIn();
        }else{
            Toast.makeText(this.context,"Пользователь уже зашел. Email = " + emailSetting(), Toast.LENGTH_LONG).show();
            return isLoggedIn();
        }
    }

    //Выход из системы
    public void logoutUser(){
        // очищаем все данные в Shared Preferences
        editor.clear();
        editor.commit();
        // Запускаем активность на вход
        Intent i = new Intent(this.context, LoginActivity.class);
        // ЗАкрываем все активности. Нашу наверх
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Новых флаг на запуск активности
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Запуск активности
        this.context.startActivity(i);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap();
        // Почта
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        // Пароль
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        //настройки
        user.put(KEY_SETTING, String.valueOf(pref.getInt(KEY_SETTING, 0)));
        return user;
    }
    //Вытаскивания ключа входа
    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_LOGIN, false);
    }
    public String emailSetting(){
        return pref.getString(KEY_EMAIL, "Пусто");
    }
    public int settingApp(){
        return pref.getInt(KEY_SETTING, 0);
    }
    //заменя настрйоки если они отличаются
    public void changeSetting(int setting){
        int alreadySetting = pref.getInt(KEY_SETTING,0);
        if(setting != alreadySetting){
            editor.putInt(KEY_SETTING, setting);
            editor.commit();
        }
    }
}
