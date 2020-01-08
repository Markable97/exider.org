package com.example.markable.footballapptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.textfield.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.markable.footballapptest.Classes.ConnectWithServer;
import com.example.markable.footballapptest.Classes.MessageRegister;
import com.example.markable.footballapptest.Classes.MessageToJson;
import com.example.markable.footballapptest.Classes.PublicConstants;
import com.example.markable.footballapptest.Classes.SessionManager;
import com.google.gson.Gson;

import java.util.HashMap;


public class LoginActivity extends Activity implements View.OnClickListener{
    private static final String TAG = LoginActivity.class.getSimpleName();
    private final String IP = PublicConstants.IP;
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private TextInputEditText inputPassword;
    SessionManager session;
    Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (TextInputEditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        btnLogin.setOnClickListener(this);
        btnLinkToRegister.setOnClickListener(this);
        session = new SessionManager(getApplicationContext());
        if (session.checkLogin()){
            HashMap<String, String> user_info = session.getUserDetails();
            String mEmail = user_info.get(session.KEY_EMAIL);
            String mPassword =   user_info.get(session.KEY_PASSWORD);
            inputEmail.setText(mEmail);
            inputPassword.setText(mPassword);
            MessageToJson message = new MessageToJson("login",
                    new MessageRegister(mEmail,
                           mPassword));
            Thread thread = new Thread(new ThreadLogin(mEmail, mPassword, message));
            thread.start();
        }
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String msgHandler = msg.getData().getString("msg");
                if(msgHandler.equals("bad")){
                    Log.i(TAG, msgHandler);
                    Toast.makeText(getApplicationContext(),"Не удалось подключиться к серверу",
                            Toast.LENGTH_SHORT).show();
                    btnLogin.setEnabled(true);
                    inputEmail.setEnabled(true);
                    inputPassword.setEnabled(true);
                    /*Intent intent = new Intent(getApplicationContext(),AddMatchActivity.class);
                    startActivity(intent);
                    finish();*/
                }else if (msgHandler.equals("success")){
                    Log.i(TAG, msgHandler);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Не верный логин или пароль",
                            Toast.LENGTH_SHORT).show();
                    btnLogin.setEnabled(true);
                    inputEmail.setEnabled(true);
                    inputPassword.setEnabled(true);
                }
            }
        };

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                Log.i(TAG, "Нажата кнопка btnLogin");
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    MessageToJson message = new MessageToJson("login", new MessageRegister(email, password));
                    Thread thread = new Thread(new ThreadLogin(email, password, message));
                    thread.start();
                    inputEmail.setEnabled(false);
                    inputPassword.setEnabled(false);
                    btnLogin.setEnabled(false);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Заполните поля!", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case R.id.btnLinkToRegisterScreen:
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

    class ThreadLogin implements Runnable{


        MessageToJson message;
        String email;
        String password;
        ConnectWithServer connect = new ConnectWithServer();

        public ThreadLogin(String email, String password, MessageToJson message) {
            this.email = email;
            this.password = password;
            this.message = message;

        }

        @Override
        public void run() {
            Log.i(TAG, "************************Запущен поток------------------------");
            Gson gson = new Gson();
            try{
                connect.openConnection();
                //connect.onlySendDate(new Gson().toJson(message));
                //Log.v(TAG,  "Данные успешно ушли. Закрытие********");
                String responseServer = connect.connectToServer(gson.toJson(message));//connect.responseFromServer(gson.toJson(message));
                Log.i(TAG, "Данные успешно ушли и пришли. Закрытие******** \n Ответ сервера: " + responseServer);
                final MessageToJson response = gson.fromJson(responseServer,MessageToJson.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.getResponseFromServer().equals("Password successfull")){
                            if(!session.isLoggedIn()){
                                session.createSetting(email,password,response.getSettingForApp());
                                Log.i(TAG, "Пароль подошел! Сохраняем в настрйоки email = " +email+" password = " + password);
                            }else{
                                Log.i(TAG, "Старые настрйоки = " + session.settingApp());
                                session.changeSetting(response.getSettingForApp());
                                Log.i(TAG, "Новые настройки = " + session.settingApp());
                                Log.i(TAG, "Пароль подошел");
                            }
                            //Отправка в GUI поток
                            connect.closeConnection();
                            handler.sendMessage(PublicConstants.getMessage("success","msg"));
                        }
                        else {
                            //Отправка в GUI поток
                            connect.closeConnection();
                            handler.sendMessage(PublicConstants.getMessage("not_success","msg"));
                        }
                    }
                });


            }catch (Exception e){
                Log.i(TAG, "ERROR \n" + e.getMessage());
                connect.closeConnection();
                connect = null;
                handler.sendMessage(PublicConstants.getMessage("bad","msg"));
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //Toast.makeText(getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onDestroy()");
    }
}
