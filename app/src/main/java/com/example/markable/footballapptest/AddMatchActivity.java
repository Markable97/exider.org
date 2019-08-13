package com.example.markable.footballapptest;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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

import com.example.markable.footballapptest.Adapters.RecyclerViewAddMatches;
import com.example.markable.footballapptest.Classes.ConnectWithServer;
import com.example.markable.footballapptest.Classes.MessageToJson;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.Schedule;
import com.example.markable.footballapptest.Classes.Stadiums;
import com.example.markable.footballapptest.Fragments.DialogTest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.function.Predicate;


public class AddMatchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnTouchListener, View.OnClickListener {

    private static final String TAG = AddMatchActivity.class.getSimpleName();
    ArrayList<String> tours = new ArrayList<>();
    private ArrayList<NextMatches> gamesInTour = new ArrayList<>();
    ArrayList<Schedule> forServerDB = new ArrayList<>();
    ArrayList<Stadiums> stadiumsList = new ArrayList<>();
    ArrayList<Schedule> scheduleList = new ArrayList<>();
    RecyclerViewAddMatches adapter;
    boolean firsLaunch = true;
    Spinner spDivision;
    Spinner spTour;
    RecyclerView recyclerView;
    EditText dateTour;
    Button btnSend;
    int[] forServer = new int[2];
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
        addInformation(11);
        spDivision = (Spinner) findViewById(R.id.spinner_diviisions);
        spTour = (Spinner) findViewById(R.id.spinner_tour);
        recyclerView = (RecyclerView) findViewById(R.id.listAddMatches);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Обработка нажатия на список в RecyclerView
        RecyclerViewAddMatches.OnAddMatchClickListener listener = new RecyclerViewAddMatches.OnAddMatchClickListener(){
            @Override
            public void onMatchClick(final NextMatches match, int check) {
                if (check == 1){
                    DialogTest test = DialogTest.newInstance(match);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    test.show(transaction, "dlgAddMatch");
                }else{
                    AlertDialog.Builder bulder = new AlertDialog.Builder(AddMatchActivity.this);
                    bulder.setTitle("Изменить?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String str[] = match.getDate().split(" ");
                                    String date = str[0];
                                    String time = str[1];
                                    Log.i(TAG, "Данные по клику: \n" + match.toString());
                                    Log.i(TAG, "Расписание: \n " + scheduleList.toString());
                                    for(Schedule s : scheduleList){
                                        if(s.getMatch_date().equals(date) && s.getMatch_time().equals(time)
                                                && s.getName_stadium().equals(match.getNameStadium())){
                                            s.setBusy_time(0);
                                            deleteScheduleForServer(s);
                                        }
                                    }
                                    for(NextMatches m : gamesInTour){
                                        if(m.getIdMatch() == match.getIdMatch()){
                                            match.setDate("");
                                            match.setNameStadium("");
                                        }
                                    }
                                    adapter.update(gamesInTour, scheduleList);
                                }
                            })
                            .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }
            }
        };
        adapter = new RecyclerViewAddMatches(gamesInTour, listener/*getSupportFragmentManager()*/);
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

        dateTour = (EditText)findViewById(R.id.editText_dateTour);
        dateTour.setOnTouchListener(this);
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        beginPicker = new DatePickerDialog(AddMatchActivity.this, beginDateLister, mYear, mMonth, mDay );

        btnSend = (Button)findViewById(R.id.btn_sendSchedule);
        btnSend.setOnClickListener(this);
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
    //Здесь возвращается то, что было выбрано в диалоговом окне с расписанием
    public void clickSchedule(Schedule test){
        if(test == null){
            Log.i(TAG, "Время занято!!");
        }else{
            Log.i(TAG, "Выбрано время из доступного расписания: \n" + test.toString());
            addInfoInMatch(test);
        }
        //Toast.makeText(getApplicationContext(),test.toString(), Toast.LENGTH_LONG).show();
    }

    private void addInfoInMatch(Schedule test) {
        for(NextMatches match : gamesInTour){
            if(match.getIdMatch() == test.getId_match()){
                match.setNameStadium(test.getName_stadium());
                match.setDate(test.getMatch_date() + " " + test.getMatch_time());
            }
        }
        adapter.update(gamesInTour);
        addScheduleForServer(test);
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    @TargetApi(Build.VERSION_CODES.N)
    void deleteScheduleForServer(final Schedule test){
        if (Build.VERSION.SDK_INT >= 23){
            Log.i(TAG, "Выполнилось API >= 23");
            forServerDB.removeIf(new Predicate<Schedule>() {
                @Override
                public boolean test(Schedule x) {
                    return x.getMatch_time().equals(test.getMatch_time()) && x.getMatch_date().equals(test.getMatch_date()) &&
                            x.getName_stadium().equals(test.getName_stadium());
                }
            });
        }else{
            Log.i(TAG, "Выполнилось API < 23");
            Iterator<Schedule> iterator = forServerDB.iterator();
            while (iterator.hasNext()){
                Schedule s = iterator.next();
                if(s.getMatch_date().equals(test.getMatch_date()) && s.getMatch_time().equals(test.getMatch_time())
                        && s.getName_stadium().equals(test.getName_stadium()) ){
                    iterator.remove();
                }
            }
        }

    }

    void addScheduleForServer(Schedule test){
        if(!forServerDB.isEmpty()){
            boolean f = true;
            for(Schedule s : forServerDB){
                if(s.getMatch_date().equals(test.getMatch_date()) && s.getMatch_time().equals(test.getMatch_time())
                        && s.getName_stadium().equals(s.getName_stadium())){
                    s = test;
                    forServerDB.add(s);
                    f = false;
                }
            }
            if(f){
                forServerDB.add(test);
            }
        }else{
            forServerDB.add(test);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        beginPicker.show();
        forServer[0] = spDivision.getSelectedItemPosition() + 1;
        forServer[1] = spTour.getSelectedItemPosition() + 1;

        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_sendSchedule){
            Log.i(TAG, "Отправка для сервера новыых значений: \n" + forServerDB);
        }
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
            message.setDate(strDB);
            try{
                connect.openConnection(); //открывваем соединение
                fromServer = connect.responseFromServer(gson.toJson(message));//получаем список игр
                gamesInTour.clear();
                gamesInTour = gson.fromJson(fromServer, new TypeToken<ArrayList<NextMatches>>(){}.getType());
                /*message.setMessageLogic("getCntStadium");
                fromServer = connect.responseFromServer(gson.toJson(message));
                countStadium = Integer.valueOf(fromServer);*/
                message.setMessageLogic("getStadiumList");
                fromServer = connect.responseFromServer(gson.toJson(message));
                stadiumsList.clear();
                stadiumsList = gson.fromJson(fromServer, new TypeToken<ArrayList<Stadiums>>(){}.getType());
                message.setMessageLogic("getScheduleList");
                fromServer = connect.responseFromServer(gson.toJson(message));
                scheduleList.clear();
                scheduleList = gson.fromJson(fromServer, new TypeToken<ArrayList<Schedule>>(){}.getType());
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
            forServerDB.clear();
            //addStadiums(4);
            //addSchedule(9);
            if (s.equals("success")){
                Log.i(TAG,"Игры в туре: \n" + gamesInTour.toString());
                Log.i(TAG,"Список стадионов: \n" + stadiumsList.toString());
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

    /*void addStadiums(int n){
        for(int i = 1; i <= n; i++){
            stadiumsList.add(new Stadiums(i, "Спартак - " + i));
        }
    }*/

    /*void addSchedule(int n){
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
    }*/

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
