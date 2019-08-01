package com.example.markable.footballapptest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.markable.footballapptest.Classes.ConnectWithServer;
import com.example.markable.footballapptest.Classes.MessageToJson;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AddMatchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = AddMatchActivity.class.getSimpleName();
    ArrayList<String> tours = new ArrayList<>();
    Spinner spDivision;
    Spinner spTour;
    int[] forServer = new int[2];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);
        addInformation(11);
        spDivision = (Spinner) findViewById(R.id.spinner_diviisions);
        spTour = (Spinner) findViewById(R.id.spinner_tour);

        ArrayAdapter<String> adapterTour = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,
                tours);
        spTour.setAdapter(adapterTour);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.divisions_list));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDivision.setAdapter(adapter);
        spDivision.setOnItemSelectedListener(this);
        spTour.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        ArrayAdapter<String> arrayTour = (ArrayAdapter<String>) spTour.getAdapter();
        ArrayAdapter<String> arrayDiv = (ArrayAdapter<String>) spDivision.getAdapter();
        switch (parent.getId()){
            case R.id.spinner_diviisions:
                Toast.makeText(getApplicationContext(),"Выбрано - " + parent.getItemAtPosition(position)
                        + " номер - " + position,Toast.LENGTH_SHORT).show();
                //forServer[0] = position + 1;

                switch (position){
                    case 0:
                        changeTour(11);
                        arrayTour.notifyDataSetChanged();
                        break;
                    case 1:
                        changeTour(21);
                        arrayTour.notifyDataSetChanged();
                        break;
                }
                break;
            case R.id.spinner_tour:
                Toast.makeText(this, "Выбран тур = " + parent.getItemAtPosition(position),
                        Toast.LENGTH_SHORT).show();
                forServer[0] = spDivision.getSelectedItemPosition() + 1;
                forServer[1] = position + 1;
                new MainServerConnect().execute(forServer[0], forServer[1]);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class MainServerConnect  extends AsyncTask<Integer, Void, String>{

        ConnectWithServer connect = new ConnectWithServer();
        Gson gson = new Gson();
        String fromServer;

        @Override
        protected String doInBackground(Integer... integers) {
            Log.i(TAG, "doInBackground: начало потока!!!!!!!!!!!!!!!!!!!!!");
            Log.i(TAG,"Дивизион = " + integers[0] + " Тур = " + integers[1]);
            MessageToJson message = new MessageToJson("getTour",integers[0], integers[1]);
            try{
                connect.openConnection(); //открывваем соединение
                fromServer = connect.responseFromServer(gson.toJson(message));//получаем список игроков
                connect.closeConnection();
                return "success";
            }catch (Exception e){
                Log.i(TAG, "ERROR \n" + e.getMessage());
                connect.closeConnection();
                connect = null;
                return "bad"; //если какакя-то ошибка возвращаем плохо
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            forServer[0] = 0;
            forServer[1] = 0;
            if (s.equals("success")){
                Log.i(TAG, "Данные от сервера \n" + fromServer);
            }else{
                Toast.makeText(getApplicationContext(),"Ошибка соединения", Toast.LENGTH_LONG).show();
            }
        }
    }

    void addInformation(int n){
        for(int i = 1; i <= n; i++){
            tours.add("Тур " + i);
        }
    }
    void changeTour(int n){
        tours.clear();
        for(int i = 1; i <= n; i++){
            tours.add("Тур " + i);
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
