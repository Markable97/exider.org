package com.example.markable.footballapptest;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.markable.footballapptest.Adapters.RecyclerViewAddResults;
import com.example.markable.footballapptest.Classes.ConnectWithServer;
import com.example.markable.footballapptest.Classes.MessageToJson;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddResultsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnTouchListener, View.OnClickListener {

    private static final String TAG = AddResultsActivity.class.getSimpleName();

    ConnectWithServer connect = new ConnectWithServer();
    Gson gson = new Gson();

    private ArrayList<NextMatches> gamesInTour = new ArrayList<>();

    boolean firsLaunch = true;
    int[] forServer = new int[2];

    Spinner spDivision;
    Spinner spTour;
    RecyclerView recyclerView;
    EditText dateTour;
    Button btnSend;

    private List<PrevMatches> matches = new ArrayList<>();
    ArrayList<String> tours = new ArrayList<>();

    //ждя работы с датой
    String strDB;
    DatePickerDialog beginPicker;
    private int mYear, mMonth, mDay;
    DatePickerDialog.OnDateSetListener beginDateLister = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String monthNew = "", dayNew = "";
            if (month + 1 < 10){
                monthNew = "0" + (month + 1);
            }else {
                monthNew = String.valueOf(month + 1);
            }
            if (dayOfMonth < 10){
                dayNew = "0" + dayOfMonth;
            }else{
                dayNew = String.valueOf(dayOfMonth);
            }
            String str = dayNew + "." + monthNew + "." + year;
            strDB = year + "-" + monthNew + "-" + dayNew;
            Log.i(TAG, "Строка для телефона: " + str +
                    "\n Строка для БД: " + strDB);
            dateTour.setText(str);
            new MainServerConnect().execute(forServer[0], forServer[1]);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);

        spDivision = (Spinner) findViewById(R.id.spinner_diviisions);
        spTour = (Spinner) findViewById(R.id.spinner_tour);
        recyclerView = (RecyclerView) findViewById(R.id.listAddMatches);
        dateTour = (EditText)findViewById(R.id.editText_dateTour);
        btnSend = (Button)findViewById(R.id.btn_sendSchedule);
        btnSend.setOnClickListener(this);

        //адаптеры для тура и дивизиона
        changeTour(15);
        ArrayAdapter<String> adapterTour = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,
                tours);
        spTour.setAdapter(adapterTour);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.divisions_list));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDivision.setAdapter(adapter);
        spDivision.setOnItemSelectedListener(this);
        spTour.setOnItemSelectedListener(this);

        //выьаскивание сегодняшней даты и слушатекль на editText
        dateTour.setOnTouchListener(this);
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        beginPicker = new DatePickerDialog(AddResultsActivity.this, beginDateLister, mYear, mMonth, mDay );

    }

    void changeTour(int n){
        tours.clear();
        for(int i = 1; i <= n; i++){
            tours.add("Тур " + i);
        }
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        beginPicker.show();
        //forServer[0] = spDivision.getSelectedItemPosition() + 1;
        //forServer[1] = spTour.getSelectedItemPosition() + 1;
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_sendSchedule){
            Toast.makeText(this, "Нажата кнопка", Toast.LENGTH_LONG).show();
        }

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
                    if(dateTour.getText().length()!=0){
                        forServer[0] = position + 1;
                        forServer[1] = spTour.getSelectedItemPosition() + 1;
                        new MainServerConnect().execute(forServer[0], forServer[1]);
                        spDivision.setEnabled(false);
                        spTour.setEnabled(false);
                    }
                }
                break;
            case R.id.spinner_tour:
                if(dateTour.getText().length()!=0){
                    forServer[0] = spDivision.getSelectedItemPosition() + 1;
                    forServer[1] = position + 1;
                    new MainServerConnect().execute(forServer[0], forServer[1]);
                    spDivision.setEnabled(false);
                    spTour.setEnabled(false);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private class MainServerConnect  extends AsyncTask<Integer, Void, String> {
        String fromServer;

        @Override
        protected String doInBackground(Integer... integers) {
            Log.i(TAG, "doInBackground: начало потока!!!!!!!!!!!!!!!!!!!!!");
            Log.i(TAG,"Дивизион = " + integers[0] + " Тур = " + integers[1]);
            MessageToJson message = new MessageToJson("getTour",integers[0], integers[1]);
            message.setDate(strDB);
            try {
                connect.openConnection(); //открывваем соединение
                fromServer = connect.responseFromServer(gson.toJson(message));//получаем список игр
                gamesInTour.clear();
                gamesInTour = gson.fromJson(fromServer, new TypeToken<ArrayList<NextMatches>>(){}.getType());
                connect.closeConnection();
                return "success";
            }
            catch (Exception e){
                return "bad";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            forServer[0] = 0;
            forServer[1] = 0;
            //forServerDB.clear();
            //addStadiums(4);
            //addSchedule(9);
            if (s.equals("success")){
                Log.i(TAG,"Игры в туре: \n" + gamesInTour.toString());
               // Log.i(TAG,"Список стадионов: \n" + stadiumsList.toString());
                //Log.i(TAG,"Расписвник: \n" + scheduleList.toString());
                //adapter.update(gamesInTour, scheduleList, stadiumsList);
            }else{
                Toast.makeText(getApplicationContext(),"Ошибка соединения", Toast.LENGTH_LONG).show();
            }
            firsLaunch =false;
            spDivision.setEnabled(true);
            spTour.setEnabled(true);
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
