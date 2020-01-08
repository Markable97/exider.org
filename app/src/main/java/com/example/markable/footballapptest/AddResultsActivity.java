package com.example.markable.footballapptest;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.markable.footballapptest.Adapters.RecyclerViewAddMatches;
import com.example.markable.footballapptest.Classes.ConnectWithServer;
import com.example.markable.footballapptest.Classes.MessageToJson;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;

public class AddResultsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnTouchListener{

    private static final String TAG = AddResultsActivity.class.getSimpleName();

    ConnectWithServer connect = new ConnectWithServer();
    Gson gson = new Gson();
    RecyclerViewAddMatches adapter;

    private ArrayList<NextMatches> gamesInTour = new ArrayList<>();

    boolean firsLaunch = true;
    int[] forServer = new int[2];

    Spinner spDivision;
    Spinner spTour;
    RecyclerView recyclerView;
    EditText dateTour;
    Button btnSend;


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
            new MainServerConnect().execute(forServer[0]);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        spDivision = (Spinner) findViewById(R.id.spinner_diviisions);
        spTour = (Spinner) findViewById(R.id.spinner_tour);
        recyclerView = (RecyclerView) findViewById(R.id.listAddMatches);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dateTour = (EditText)findViewById(R.id.editText_dateTour);
        btnSend = (Button)findViewById(R.id.btn_sendSchedule);
        btnSend.setVisibility(View.GONE); // скрытие элемента чтобы не создавать еще один макет

        //listener
        RecyclerViewAddMatches.OnAddMatchClickListener listener = new RecyclerViewAddMatches.OnAddMatchClickListener() {
            @Override
            public void onMatchClick(final NextMatches match, int check) {
                //Toast.makeText(getApplicationContext(), "Нажат:\n" + match.toString(), Toast.LENGTH_LONG).show();
                if(match.getPlayed() == 1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddResultsActivity.this);
                    builder.setTitle("Матч уже сыгран - изменить?")
                            .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    transitionActivity(match, 2);
                                }
                            }).show();
                }else{
                    transitionActivity(match, 1);
                }
            }
        };
        //адаптер для recyclerView
        adapter = new RecyclerViewAddMatches(gamesInTour, listener/*getSupportFragmentManager()*/);
        recyclerView.setAdapter(adapter);

        //адаптеры для дивизиона
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.divisions_list));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDivision.setAdapter(adapter);
        spDivision.setOnItemSelectedListener(this);
        spTour.setVisibility(View.GONE); // скрытие элемента чтобы не создавать еще один макет

        //выьаскивание сегодняшней даты и слушатекль на editText
        dateTour.setOnTouchListener(this);
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        beginPicker = new DatePickerDialog(AddResultsActivity.this, beginDateLister, mYear, mMonth, mDay );

    }

    void transitionActivity(NextMatches match, int options){
        Intent intent = new Intent(getApplicationContext(), AddProtocolActivity.class);
        intent.putExtra("protocol", match);
        intent.putExtra("optionsDB", options);
        startActivity(intent);
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        beginPicker.show();
        forServer[0] = spDivision.getSelectedItemPosition() + 1;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        //ArrayAdapter<String> arrayDiv = (ArrayAdapter<String>) spDivision.getAdapter();
        switch (parent.getId()){
            case R.id.spinner_diviisions:
                /*Toast.makeText(getApplicationContext(),"Выбрано - " + parent.getItemAtPosition(position)
                        + " номер - " + position,Toast.LENGTH_SHORT).show();*/
                //forServer[0] = position + 1;

                if(!firsLaunch){
                    if(dateTour.getText().length()!=0){
                        forServer[0] = position + 1;
                        new MainServerConnect().execute(forServer[0], forServer[1]);
                        spDivision.setEnabled(false);
                        spTour.setEnabled(false);
                    }
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
            Log.i(TAG,"Дивизион = " + integers[0]);
            MessageToJson message = new MessageToJson("getTourAddResult",integers[0]);
            message.setDate(strDB);
            try {
                connect.openConnection(); //открывваем соединение
                fromServer = connect.responseFromServer(gson.toJson(message));//получаем список игр
                gamesInTour.clear();
                gamesInTour = gson.fromJson(fromServer, new TypeToken<ArrayList<NextMatches>>(){}.getType());
                connect.closeConnection();
                if (gamesInTour.size() != 0){
                    return "success";
                }else{
                    return "empty";
                }
            }
            catch (Exception e){
                return "bad";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            forServer[0] = 0;
            switch(s){
                case "success":
                    Log.i(TAG,"Игры в туре: \n" + gamesInTour.toString());
                    adapter.update(gamesInTour);
                    break;
                case "empty":
                    Toast.makeText(getApplicationContext(),"Нет матчей для заполнения", Toast.LENGTH_LONG).show();
                    adapter.update(gamesInTour);
                    break;
                case "bad":
                    Toast.makeText(getApplicationContext(),"Ошибка соединения", Toast.LENGTH_LONG).show();
                    break;
            }
            firsLaunch =false;
            spDivision.setEnabled(true);
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
