package com.example.markable.footballapptest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.markable.footballapptest.Adapters.RecyclerViewAddMatches;
import com.example.markable.footballapptest.Classes.ConnectWithServer;
import com.example.markable.footballapptest.Classes.MessageToJson;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.Schedule;
import com.example.markable.footballapptest.Classes.Stadiums;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class AddMatchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = AddMatchActivity.class.getSimpleName();
    ArrayList<String> tours = new ArrayList<>();
    private ArrayList<NextMatches> gamesInTour = new ArrayList<>();
    ArrayList<Stadiums> stadiumsList = new ArrayList<>();
    ArrayList<Schedule> scheduleList = new ArrayList<>();
    RecyclerViewAddMatches adapter;
    boolean firsLaunch = true;
    Spinner spDivision;
    Spinner spTour;
    RecyclerView recyclerView;
    int[] forServer = new int[2];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);
        addInformation(11);
        spDivision = (Spinner) findViewById(R.id.spinner_diviisions);
        spTour = (Spinner) findViewById(R.id.spinner_tour);
        recyclerView = (RecyclerView) findViewById(R.id.listAddMatches);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAddMatches(gamesInTour, getSupportFragmentManager());
        recyclerView.setAdapter(adapter);

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
        //ArrayAdapter<String> arrayDiv = (ArrayAdapter<String>) spDivision.getAdapter();
        switch (parent.getId()){
            case R.id.spinner_diviisions:
                /*Toast.makeText(getApplicationContext(),"Выбрано - " + parent.getItemAtPosition(position)
                        + " номер - " + position,Toast.LENGTH_SHORT).show();*/
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
                if(!firsLaunch){
                    forServer[0] = position + 1;
                    forServer[1] = spTour.getSelectedItemPosition() + 1;
                    new MainServerConnect().execute(forServer[0], forServer[1]);
                    spDivision.setEnabled(false);
                    spTour.setEnabled(false);
                }
                break;
            case R.id.spinner_tour:
                /*Toast.makeText(this, "Выбран тур = " + parent.getItemAtPosition(position),
                        Toast.LENGTH_SHORT).show();*/
                forServer[0] = spDivision.getSelectedItemPosition() + 1;
                forServer[1] = position + 1;
                new MainServerConnect().execute(forServer[0], forServer[1]);
                spDivision.setEnabled(false);
                spTour.setEnabled(false);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void test(String test){
        Toast.makeText(getApplicationContext(),test, Toast.LENGTH_LONG).show();
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
                fromServer = connect.responseFromServer(gson.toJson(message));//получаем список игро
                gamesInTour.clear();
                gamesInTour = gson.fromJson(fromServer, new TypeToken<ArrayList<NextMatches>>(){}.getType());
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
            addStadiums(4);
            addSchedule(9);
            if (s.equals("success")){
                Log.i(TAG,"Игры в туре: \n" + gamesInTour.toString());
                Log.i(TAG,"Расписвник: \n" + scheduleList.toString());
                adapter.update(gamesInTour, scheduleList, stadiumsList);
            }else{
                Toast.makeText(getApplicationContext(),"Ошибка соединения", Toast.LENGTH_LONG).show();
            }
            firsLaunch =false;
            spDivision.setEnabled(true);
            spTour.setEnabled(true);
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

    void addStadiums(int n){
        for(int i = 1; i <= n; i++){
            stadiumsList.add(new Stadiums(i, "Спартак - " + i));
        }
    }

    void addSchedule(int n){
        int time = 9;
        int checked = 0;
        for(int i = 1; i <= stadiumsList.size(); i++){
            for(int j = 1; j <= n; j++){
                if(j % 2 == 0){
                    checked = 0;
                }else{
                    checked = 1;
                }
                scheduleList.add(new Schedule(String.valueOf(9+j) + ": 20",
                        new Stadiums(i, "Спартак - " + i), checked));
            }
        }
    }

    public ArrayList<Schedule> getScheduleList(){
        return scheduleList;
    }
    public ArrayList<Stadiums> getStadiumsList(){
        return stadiumsList;
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
