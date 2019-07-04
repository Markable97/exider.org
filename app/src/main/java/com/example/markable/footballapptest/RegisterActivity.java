package com.example.markable.footballapptest;

import android.app.Activity;
import android.content.Intent;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String team = inputTeamName.getText().toString().trim();
                MessageRegister register = new MessageRegister(name, email, team, password);
                MessageToJson message = new MessageToJson("register", register);
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !team.isEmpty()) {
                    //registerUser(name, email, password);
                    boolean connection = new TestConnection().isConecctedToInternet();
                    if (connection == false){
                        Toast.makeText(getApplicationContext(),
                                "Нет соединения с интернетом", Toast.LENGTH_LONG)
                                .show();
                    }
                    else {
                    boolean connectPort = new TestConnection().isPortOpen(PublicConstants.IP,PublicConstants.port);
                    if(connectPort== false){
                        Toast.makeText(getApplicationContext(),
                                "Нет соединения с сервером", Toast.LENGTH_LONG)
                                .show();
                    }//add commit
                    else{
                    new MainServerConnect().execute(message);}}
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



    public class MainServerConnect extends AsyncTask {
        MessageToJson message;
        String response;
        @Override
        protected Object doInBackground(Object[] objects) {

            message = (MessageToJson) objects[0];
            Socket socket;
            Gson gson = new Gson();
            try {
                socket = new Socket(IP, PublicConstants.port);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String json = gson.toJson(message);
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
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (response.equals("Все нормасно")){
                Toast.makeText(getApplicationContext(), "Введите логин и пароль", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Неудача попробуйте снова!", Toast.LENGTH_LONG).show();
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
