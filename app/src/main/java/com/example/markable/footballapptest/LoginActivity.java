package com.example.markable.footballapptest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
            MessageToJson message = new MessageToJson("login",
                    new MessageRegister(user_info.get(session.KEY_EMAIL),
                            user_info.get(session.KEY_PASSWORD)));
            new MainServerConnect().execute(message);
        }else{
            inputEmail = (EditText) findViewById(R.id.email);
            inputPassword = (EditText) findViewById(R.id.password);
            btnLogin = (Button) findViewById(R.id.btnLogin);
            btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
            btnLogin.setOnClickListener(this);
            btnLinkToRegister.setOnClickListener(this);
        }

    }
//new test
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
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
                    boolean connection = new TestConnection().isConecctedToInternet();
                    if (connection == false){
                        Toast.makeText(getApplicationContext(),
                                "Нет соединения с интернетом", Toast.LENGTH_LONG)
                                .show();
                    }
                    else{boolean connectPort = new TestConnection().isPortOpen(PublicConstants.IP,PublicConstants.port);
                        if(connectPort== false){
                            Toast.makeText(getApplicationContext(),
                                    "Нет соединения с сервером", Toast.LENGTH_LONG)
                                    .show();
                        }
                        else{
                        	session.createSetting(email,password);
                            new MainServerConnect().execute(message);}}
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

    public class MainServerConnect extends AsyncTask<MessageToJson, Void, String> {
        String response;
        MessageToJson messageToJson;
        Gson gson = new Gson();
        @Override
        protected String doInBackground(MessageToJson... messageToJsons) {
            for (MessageToJson message : messageToJsons){
                messageToJson = message;
            }
            Socket socket;
            Gson gson = new Gson();
            try {
                socket = new Socket(IP, PublicConstants.port);

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String json = gson.toJson(messageToJson);
                out.writeUTF(json);
                response = in.readUTF();
                out.writeUTF("{\"messageLogic\":\"close\"}");
                out.close();
                in.close();
                //inResultsPrev.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MessageToJson response = gson.fromJson(this.response,MessageToJson.class);
            if (response.getResponseFromServer().equals("Password successfull")){
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),
                        "Неверный логин и пароль! " + response.getResponseFromServer(), Toast.LENGTH_LONG)
                        .show();
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
