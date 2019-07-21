package com.example.markable.footballapptest;

import android.app.Activity;
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
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    final String IP = PublicConstants.IP;
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputTeamName;
    private EditText inputEmail;
    private EditText inputPassword;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.name);
        inputTeamName = (EditText) findViewById(R.id.teamName);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        btnRegister.setOnClickListener(this);
        btnLinkToLogin.setOnClickListener(this);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String msgHandler = msg.getData().getString("msg");
                if(msgHandler.equals("bad")){
                    Log.i(TAG, msgHandler);
                    Toast.makeText(getApplicationContext(),"Не удалось подключиться к серверу",
                            Toast.LENGTH_SHORT).show();
                    btnRegister.setEnabled(true);
                }else if(msgHandler.equals("success")){
                    Log.i(TAG, msgHandler);
                    Toast.makeText(getApplicationContext(), "Введите логин и пароль", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Log.i(TAG, msgHandler + " Что-то пощло не так");
                    btnRegister.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Что-то пошло не так попробуйте снова ", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String team = inputTeamName.getText().toString().trim();
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !team.isEmpty()) {
                    MessageRegister register = new MessageRegister(name, email, team, password);
                    MessageToJson message = new MessageToJson("register", register);
                    Thread thread = new Thread(new ThreadRegister(message));
                    thread.start();
                    btnRegister.setEnabled(false);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Заполните поля!", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case R.id.btnLinkToLoginScreen:
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

    class ThreadRegister implements Runnable{

        MessageToJson message;
        ConnectWithServer connect = new ConnectWithServer();

        public ThreadRegister(MessageToJson message) {
            this.message = message;
        }

        @Override
        public void run() {
            Log.v(TAG, "************************Поток запущен*******************************");
            Gson gson = new Gson();
            try{
                connect.openConnection();
                String responseServer = connect.responseFromServer(gson.toJson(message));
                Log.i(TAG, "Данные успешно ушли и пришли. Закрытие******** \n Ответ сервера: " + responseServer);
                final MessageToJson response = gson.fromJson(responseServer, MessageToJson.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.getResponseFromServer().equals("SUCCESS")){
                            Log.i(TAG, "Пользователь зарегистрирован");
                            connect.closeConnection();
                            handler.sendMessage(PublicConstants.getMessage("success", "msg"));
                        }else{
                            Log.i(TAG, "Что-то пошло не так. Ошибка БД");
                            connect.closeConnection();
                            handler.sendMessage(PublicConstants.getMessage("not_success", "msg"));
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
