package com.example.markable.footballapptest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.markable.footballapptest.Classes.ConnectWithServer;
import com.example.markable.footballapptest.Classes.Division;
import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.MessageToJson;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.Classes.SessionManager;
import com.example.markable.footballapptest.Classes.PublicConstants;
import com.example.markable.footballapptest.Classes.TournamentTable;
import com.example.markable.footballapptest.Fragments.FragmentMain;
import com.example.markable.footballapptest.Fragments.MyDialogFragment;
import com.example.markable.footballapptest.Fragments.MyDialogFragmentChooseDateTime;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    boolean flag = false;
    boolean flagSpinner = true;
    boolean update = false;
    int dataForFragment = 0;
    //final String IP = "10.0.2.2";
    final String IP = PublicConstants.IP;
    //В будущем можно будет предопределять данную переенную
    String nameLeague = "Западная Лига";

    private static final String TAG = "MainAct";
    FragmentMain fragmentMain;

    SessionManager manager;

    Spinner spinnerLeague;

    private ArrayList<ImageFromServer> imageArray = new ArrayList<>();
    private ArrayList<TournamentTable> tournamentTable = new ArrayList<>();
    private ArrayList<PrevMatches> prevResultsMatch = new ArrayList<>();
    private ArrayList<NextMatches> nextResultsMatch = new ArrayList<>();
    private ArrayList<Division> divisions = new ArrayList<>();
    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = findViewById(R.id.adView_main_screen);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        new MainServerConnect(MainActivity.this, 1).execute(dataForFragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setTitle(getResources().getString(R.string.high_div));
        manager = new SessionManager(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch (manager.settingApp()){
                    case 3: //Админ
                        intent = new Intent(getApplicationContext(),AddMatchActivity.class);
                        startActivity(intent);
                        break;
                    case 2: //Судья
                        intent = new Intent(getApplicationContext(),AddResultsActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        MyDialogFragment myDialogFragment = new MyDialogFragment();
                        FragmentManager manager = getSupportFragmentManager();
                        //myDialogFragment.show(manager, "dialog");
                        FragmentTransaction transaction = manager.beginTransaction();
                        myDialogFragment.show(transaction, "dialog");
                        break;
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        spinnerLeague = (Spinner)navigationView.getHeaderView(0).findViewById(R.id.spinner_leagues);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.leagues_list, R.layout.spinner_league_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLeague.setAdapter(adapter);


        int pos = adapter.getPosition(nameLeague);
        spinnerLeague.setSelection(pos);

        spinnerLeague.setOnItemSelectedListener(this);






    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            Toast.makeText(getApplicationContext(),"Данная функция пока недоступна =)", Toast.LENGTH_SHORT).show();
            /*Toast.makeText(getApplicationContext(),"Вы вышли из пользователя", Toast.LENGTH_SHORT).show();
            SessionManager session = new SessionManager(getApplicationContext());
            session.logoutUser();
            finish();
            return true;*/
        }else{
            update = true;
            if(dataForFragment==0){
                dataForFragment = divisions.get(0).idDivision;
            }
            new MainServerConnect(MainActivity.this, 3).execute(dataForFragment);
        }

        return super.onOptionsItemSelected(item);
    }

    private void addMenuItemInNavMenuDrawer(ArrayList<Division> divisions, int checkDivision) {
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        Menu menu = navView.getMenu();
        menu.clear();
        //Menu submenu = menu.addSubMenu("Дивизионы");
        for(Division d : divisions) {
            if(d.idDivision == checkDivision){
                menu.add(d.nameDivision).setCheckable(true).setChecked(true);
                setTitle(d.nameDivision);
            }else{
                menu.add(d.nameDivision).setCheckable(true);
            }

        }

        navView.invalidate();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //Fragment fragment = null;
        //Class fragmentClass = null;
        dataForFragment = 0;
        String nameDivision = item.toString();
        for(Division d : divisions){
            if(nameDivision.equals(d.nameDivision)){
                dataForFragment = d.idDivision;
            }
        }
        if(dataForFragment == 0){
            return false;
        }

        new MainServerConnect(MainActivity.this, 3).execute(dataForFragment);
    //fragmentMain.update(dataForFragment);

        /*try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }*/

        /*FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FragmentForTable fragForDiv = new FragmentForTable().newInstance(dataForFragment);
        fragmentTransaction.replace(R.id.container, fragForDiv).commit();*/
        //item.setChecked(true);
        //setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
}

    @Override
    public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
        flagSpinner = !flagSpinner;
        if(flagSpinner){
            System.out.println("Выбрана лига: " + parent.getSelectedItem().toString());
            nameLeague =  parent.getSelectedItem().toString();
            new MainServerConnect(MainActivity.this, 2).execute(dataForFragment);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class MainServerConnect extends AsyncTask<Integer, Void, String>{
        ConnectWithServer connect = new ConnectWithServer();
        //String query = "{\"id_division\":1,\"id_tour\":2}";
        String query = "";
        int setting = 0;/*1 - все сразу(дивы и все о них), 2 - только дивы, 3 - только инфо*/
        Gson gson = new Gson();
        String fromServer = null, fromServerResultsPrevMatches = null, fromServerCalendar = null ;
        //String ipAdres = "192.168.0.104";
        //String ipAdres = "92.38.241.107";
        //String ipAdres = "10.0.2.2"
        MainActivity activity;
        private Context context;
        ProgressDialog progressDialog ;
        String responseFromServer;

        public MainServerConnect(MainActivity activity, int setting){
            this.activity = activity;
            this.setting = setting;
            context = activity;
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Загрузка");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(Integer... integers) {

            Log.i(TAG, "Поток запущен");
            MessageToJson message = new MessageToJson("listDivision", integers[0]);
            try{
                connect.openConnection();
                if(setting == 2){
                    message.name_league = nameLeague;
                    responseFromServer = connect.connectToServer(gson.toJson(message));
                    divisions.clear();
                    divisions = gson.fromJson(responseFromServer, new TypeToken<ArrayList<Division>>(){}.getType());
                    Log.i(TAG, "Divisions: \n" + divisions);
                    flagSpinner = false;
                    connect.closeConnection();
                }else if(setting == 1){
                    message.name_league = nameLeague;
                    responseFromServer = connect.connectToServer(gson.toJson(message));
                    divisions.clear();
                    divisions = gson.fromJson(responseFromServer, new TypeToken<ArrayList<Division>>(){}.getType());
                    Log.i(TAG, "Divisions: \n" + divisions);
                    connect.openConnection(); //открываем соединение
                    dataForFragment= divisions.get(0).idDivision;
                    message.setId(divisions.get(0).idDivision);
                    message.setMessageLogic("division");
                    responseFromServer = connect.connectToServer(gson.toJson(message));
                    String delimiter = "\\?";
                    String[] arrayJSON = responseFromServer.split(delimiter);
                    tournamentTable.clear();
                    Log.i(TAG, arrayJSON[0]);
                    tournamentTable = gson.fromJson(arrayJSON[0], new TypeToken<ArrayList<TournamentTable>>(){}.getType());
                    Log.i(TAG, "Размер турнирной таблицы " + tournamentTable.size());
                    Log.i(TAG, arrayJSON[1]);
                    prevResultsMatch.clear();
                    try {
                        prevResultsMatch = gson.fromJson(arrayJSON[1], new TypeToken<ArrayList<PrevMatches>>() {
                        }.getType());
                    }catch(Exception e){
                        Log.i(TAG, "Bed decoding JSON prevMatches");
                    }
                    Log.i(TAG, "Размер предыдущих матчей таблицы " + prevResultsMatch.size());
                    Log.i(TAG, arrayJSON[2]);
                    nextResultsMatch.clear();
                    try{
                        nextResultsMatch = gson.fromJson(arrayJSON[2], new TypeToken<ArrayList<NextMatches>>(){}.getType());
                    }catch(Exception e){
                        Log.i(TAG, "Bed decoding JSON nextMatches");
                    }
                    Log.i(TAG, "Размер будущх матчей " + nextResultsMatch.size());
                    connect.closeConnection();
                }else{//3 - только инфа
                    message.setMessageLogic("division");
                    responseFromServer = connect.connectToServer(gson.toJson(message));
                    String delimiter = "\\?";
                    String[] arrayJSON = responseFromServer.split(delimiter);
                    tournamentTable.clear();
                    Log.i(TAG, arrayJSON[0]);
                    tournamentTable = gson.fromJson(arrayJSON[0], new TypeToken<ArrayList<TournamentTable>>(){}.getType());
                    Log.i(TAG, "Размер турнирной таблицы " + tournamentTable.size());
                    Log.i(TAG, arrayJSON[1]);
                    prevResultsMatch.clear();
                    try {
                        prevResultsMatch = gson.fromJson(arrayJSON[1], new TypeToken<ArrayList<PrevMatches>>() {
                        }.getType());
                    }catch(Exception e){
                        Log.i(TAG, "Bed decoding JSON prevMatches");
                    }
                    Log.i(TAG, "Размер предыдущих матчей таблицы " + prevResultsMatch.size());
                    Log.i(TAG, arrayJSON[2]);
                    nextResultsMatch.clear();
                    try{
                        nextResultsMatch = gson.fromJson(arrayJSON[2], new TypeToken<ArrayList<NextMatches>>(){}.getType());
                    }catch(Exception e){
                        Log.i(TAG, "Bed decoding JSON nextMatches");
                    }
                    Log.i(TAG, "Размер будущх матчей " + nextResultsMatch.size());
                    connect.closeConnection();
                }
                return "success"; //все хорошо

            }catch(Exception e) {
                Log.i(TAG, "ERROR \n" + e.getMessage());
                connect.closeConnection();
                connect = null;
                update = false;
                return "bad"; //если какакя-то ошибка возвращаем плохо
            }
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            this.progressDialog.dismiss();
            if (s.equals("success")){
                if(setting==1 || setting ==3){
                    if(flag == false){//проверка на запущенность активности
                        fragmentMain = new FragmentMain();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frameContainer, fragmentMain).commit();
                        flag = true;
                    }else{
                        if(update)
                            Toast.makeText(MainActivity.this, "Данные обновлены", Toast.LENGTH_SHORT).show();
                        fragmentMain.update();
                    }

                        addMenuItemInNavMenuDrawer(divisions, dataForFragment);

                }else{
                    addMenuItemInNavMenuDrawer(divisions, dataForFragment);
                }
            }else{
                Toast.makeText(getApplicationContext(),"Ошибка соединения", Toast.LENGTH_LONG).show();
            }

        }

    }

    public ArrayList<NextMatches> getNextResultsMatch() {
        return nextResultsMatch;
    }

    public ArrayList<TournamentTable> getTournamentTable() {
        return tournamentTable;
    }

    public ArrayList<PrevMatches> getPrevResultsMatch() {
        return prevResultsMatch;
    }

    public ArrayList<ImageFromServer> getImageArray() {
        return imageArray;
    }

    public void okClicked(String tag) {
        MyDialogFragmentChooseDateTime myDialogFragment = new MyDialogFragmentChooseDateTime();
        FragmentManager manager = getSupportFragmentManager();
        //myDialogFragment.show(manager, "dialog");
        FragmentTransaction transaction = manager.beginTransaction();
        if (tag.equals("dialog")){
            myDialogFragment.show(transaction, "dialog_choose");
        }else{
            //вернуть что нибудь;
        }
    }

    public void cancelClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку отмены!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"OnStart ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "OnResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause" );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"onStop" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy" );
    }
}
