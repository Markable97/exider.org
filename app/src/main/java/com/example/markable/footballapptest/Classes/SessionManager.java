package com.example.markable.footballapptest.Classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.markable.footballapptest.LoginActivity;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "AndroidUserPref";
    private static final String KEY_LOGIN = "loggedIN";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    public SessionManager(Context context) {
        this.context = context;
    }
   //Создание настроек
    public void createSetting(String email, String password){
        editor.putBoolean(KEY_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }
    //проверяем залогинился ли пользователь
    public boolean checkLogin(){
        Intent intent;
        if(!this.isLoggedIn()){
            Toast.makeText(this.context,"Необходимо войти", Toast.LENGTH_LONG).show();
            return isLoggedIn();
        }else{
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
        return user;
    }
    //Вытаскивания ключа входа
    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_LOGIN, false);
    }
}
