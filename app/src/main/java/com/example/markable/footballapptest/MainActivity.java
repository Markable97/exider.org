package com.example.markable.footballapptest;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.markable.footballapptest.Classes.ConnectWithServer;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean flag = false;
    //final String IP = "10.0.2.2";
    final String IP = PublicConstants.IP;

    private static final String TAG = "MainAct";
    FragmentMain fragmentMain;

    SessionManager manager;

    private ArrayList<ImageFromServer> imageArray = new ArrayList<>();
    private ArrayList<TournamentTable> tournamentTable = new ArrayList<>();
    private ArrayList<PrevMatches> prevResultsMatch = new ArrayList<>();
    private ArrayList<NextMatches> nextResultsMatch = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainServerConnect().execute(1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = new SessionManager(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Отправка заявки на игру", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = null;
                switch (manager.settingApp()){
                    case 3:
                        intent = new Intent(getApplicationContext(),AddMatchActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
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
                /*if(manager.settingApp() == 2){
                    Intent intent = new Intent(getApplicationContext(),AddMatchActivity.class);
                    startActivity(intent);
                }else{
                    MyDialogFragment myDialogFragment = new MyDialogFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    //myDialogFragment.show(manager, "dialog");
                    FragmentTransaction transaction = manager.beginTransaction();
                    myDialogFragment.show(transaction, "dialog");
                }*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




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
            Toast.makeText(getApplicationContext(),"Нажата кнопка сброса", Toast.LENGTH_SHORT).show();
            SessionManager session = new SessionManager(getApplicationContext());
            session.logoutUser();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //Fragment fragment = null;
        //Class fragmentClass = null;

        int dataForFragment = 0;

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            //fragmentClass = FragmentForTable.class;
            dataForFragment = 1;
        } else if (id == R.id.nav_gallery) {
            dataForFragment = 2;
        } else if (id == R.id.nav_slideshow) {
            dataForFragment = 3;
        } else if (id == R.id.nav_manage) {
            dataForFragment = 4;
        } else if (id == R.id.nav_share) {
            dataForFragment = 5;
        } else if (id == R.id.nav_send) {
            dataForFragment = 6;
        }

        new MainServerConnect().execute(dataForFragment);
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
        item.setChecked(true);
    setTitle(item.getTitle());

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
}

    private class MainServerConnect extends AsyncTask<Integer, Void, String>{
        ConnectWithServer connect = new ConnectWithServer();
        //String query = "{\"id_division\":1,\"id_tour\":2}";
        String query = "";
        Gson gson = new Gson();
        String fromServer = null, fromServerResultsPrevMatches = null, fromServerCalendar = null ;
        //String ipAdres = "192.168.0.104";
        //String ipAdres = "92.38.241.107";
        //String ipAdres = "10.0.2.2"


        @Override
        protected String doInBackground(Integer... integers) {

            Log.i(TAG, "Поток запущен");
            MessageToJson message = new MessageToJson("division", integers[0]);
            try{
                connect.openConnection(); //открываем соединение
                ArrayList<String> response = connect.responseFromServerArray(gson.toJson(message)); //получаем массив JSON-ов
                Log.i(TAG, response.toString());
                for(int i = 0; i < response.size(); i++){
                    switch (i){
                        case 0:
                            tournamentTable.clear();
                            Log.i(TAG, response.get(i));
                            tournamentTable = gson.fromJson(response.get(i), new TypeToken<ArrayList<TournamentTable>>(){}.getType());
                            Log.i(TAG, "Размер турнирной таблицы " + tournamentTable.size());
                            break;
                        case 1:
                            Log.i(TAG, response.get(i));
                            prevResultsMatch.clear();
                            prevResultsMatch = gson.fromJson(response.get(i), new TypeToken<ArrayList<PrevMatches>>(){}.getType());
                            Log.i(TAG, "Размер турнирной таблицы " + prevResultsMatch.size());
                            break;
                        case 2:
                            Log.i(TAG, response.get(i));
                            nextResultsMatch.clear();
                            nextResultsMatch = gson.fromJson(response.get(i), new TypeToken<ArrayList<NextMatches>>(){}.getType());
                            break;
                    }
                }
                imageArray = connect.fileFromServer();
                if(imageArray == null){
                    Log.i(TAG, "Нет фотографий");
                }

                for(PrevMatches match : prevResultsMatch){
                    for(ImageFromServer image : imageArray){
                        if(image.getNameImage().equals(match.getTeamHome())){
                            match.setImageHome(image);
                        }else if (image.getNameImage().equals(match.getTeamVisit())){
                            match.setImageVisit(image);
                        }
                    }
                }

                connect.closeConnection();
                return "success"; //все хорошо

            }catch(Exception e) {
                Log.i(TAG, "ERROR \n" + e.getMessage());
                connect.closeConnection();
                connect = null;
                return "bad"; //если какакя-то ошибка возвращаем плохо
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("success")){
                if(flag == false){//проверка на запущенность активности
                    fragmentMain = new FragmentMain();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameContainer, fragmentMain).commit();
                    flag = true;
                }else{
                    fragmentMain.update();
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
