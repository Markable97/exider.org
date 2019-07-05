package com.example.markable.footballapptest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.markable.footballapptest.Classes.ConnectWithServer;
import com.example.markable.footballapptest.Classes.MessageRegister;
import com.example.markable.footballapptest.Classes.MessageToJson;
import com.example.markable.footballapptest.Classes.PublicConstants;
import com.example.markable.footballapptest.Classes.TestConnection;
import com.example.markable.footballapptest.Classes.SessionManager;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;


public class LoginActivity extends Activity implements View.OnClickListener{
    private static final String TAG = LoginActivity.class.getSimpleName();
    private final String IP = PublicConstants.IP;
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    SessionManager session;
    Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager(getApplicationContext());
        /*boolean hasVisited = session.pref.getBoolean("hasVisited", false);
        if (!hasVisited) {
            session.editor.putBoolean("hasVisited", true);
            session.editor.commit();
        }else {
        }*/
        if (session.checkLogin()){
            HashMap<String, String> user_info = session.getUserDetails();
            String mEmail = user_info.get(session.KEY_EMAIL);
            String mPassword =   user_info.get(session.KEY_PASSWORD);
            MessageToJson message = new MessageToJson("login",
                    new MessageRegister(mEmail,
                           mPassword));
            Thread thread = new Thread(new ThreadRequest(getApplicationContext(),mEmail, mPassword, message));
            thread.start();
        }
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        btnLogin.setOnClickListener(this);
        btnLinkToRegister.setOnClickListener(this);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String msgHandler = msg.getData().getString("msg");
                if(msgHandler.equals("bad")){
                    Log.i(TAG, msgHandler);
                    Toast.makeText(getApplicationContext(),"Не удалось подключиться к серверу",
                            Toast.LENGTH_SHORT).show();
                    btnLogin.setEnabled(true);
                }else if (msgHandler.equals("success")){
                    Log.i(TAG, msgHandler);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Не верный логин или пароль",
                            Toast.LENGTH_SHORT).show();
                    btnLogin.setEnabled(true);
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
                MessageToJson message = new MessageToJson("login", new MessageRegister(email, password));
                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    //checkLogin(email, password);
                    //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    /*Intent i = new Intent(getApplicationContext(), AddResultsActivity.class);
                    startActivity(i);*/
                    Thread thread = new Thread(new ThreadRequest(getApplicationContext(),email, password, message));
                    thread.start();
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

    class ThreadRequest implements Runnable{

        Context context;
        MessageToJson message;
        String email;
        String password;
        ConnectWithServer connect = new ConnectWithServer();

        public ThreadRequest(Context context, String email, String password, MessageToJson message) {
            this.email = email;
            this.password = password;
            this.message = message;
            this.context = context;
        }

        @Override
        public void run() {
            Log.i(TAG, "************************Запущен поток------------------------");
            Gson gson = new Gson();
            try{
                connect.openConnection();
                //connect.onlySendDate(new Gson().toJson(message));
                //Log.v(TAG,  "Данные успешно ушли. Закрытие********");
                String responseServer = connect.responseFromServer(gson.toJson(message));
                Log.i(TAG, "Данные успешно ушли и пришли. Закрытие******** \n Ответ сервера: " + responseServer);
                final MessageToJson response = gson.fromJson(responseServer,MessageToJson.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.getResponseFromServer().equals("Password successfull")){
                            if(!session.checkLogin()){
                                session.createSetting(email,password);
                                Log.i(TAG, "Пароль подошел! Сохраняем в настрйоки email = " +email+" password = " + password);
                            }else{
                                Log.i(TAG, "Пароль подошел");
                            }

                            handler.sendMessage(getMessageFromString("success","msg"));
                        }
                        else {
                            handler.sendMessage(getMessageFromString("not_success","msg"));
                        }
                    }
                });


            }catch (Exception e){
                Log.i(TAG, "ERROR \n" + e.getMessage());
                connect = null;
                handler.sendMessage(getMessageFromString("bad","msg"));
            }
        }

        public Message getMessageFromString(String str, String key) {
            Bundle messageBundle = new Bundle();
            messageBundle.putString(key, str);

            Message message = new Message();
            message.setData(messageBundle);
            return message;
        }
    }
    Ы

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
