package com.example.markable.footballapptest.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.PublicConstants;
import com.example.markable.footballapptest.Classes.TournamentTable;
import com.example.markable.footballapptest.MainActivity;
import com.example.markable.footballapptest.R;
import com.example.markable.footballapptest.TeamActivity;
import com.example.markable.footballapptest.UpdateFragListener;

import java.util.ArrayList;

public class FragmentForTable extends Fragment implements UpdateFragListener{

    private static final String TAG = "FragTest";

    private String fromActivity = null;

    //private final float scale = getBaseContext().getResources().getDisplayMetrics().density;

    int _50dp, _1dp;
    int green, yellow, pink, red;


    int whiteColor;

    MainActivity activity;

    private ArrayList<TournamentTable> newTournamentTable = new ArrayList<>();
    private ArrayList<ImageFromServer> imageBitmap;

    TableLayout tableLay;
    //TextView textView;

    public static FragmentForTable newInstance(){

        FragmentForTable fragment = new FragmentForTable();

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final float scale = context.getResources().getDisplayMetrics().density;
        _50dp = (int) (49*scale*0.5f);
        _1dp = 0;//(int)(1*scale*0.5f);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "OnCreate:");
        //fromActivity = getArguments().getString("division","");
        green = ContextCompat.getColor(getActivity(),R.color.nextDiv);
        yellow = ContextCompat.getColor(getActivity(),R.color.nextDivSt);
        red = ContextCompat.getColor(getActivity(),R.color.prevDiv);
        pink = ContextCompat.getColor(getActivity(),R.color.prevDivSt);
        whiteColor = ContextCompat.getColor(getActivity(), R.color.backCell);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_table, container, false);
        Log.i(TAG, "OnCreateView: переменная для JSON " + fromActivity);
        Log.i(TAG, "OnCreateView: Загрузка окна фрагмента ");

        activity = (MainActivity)getActivity();
        newTournamentTable = activity.getTournamentTable();
        Log.i(TAG, "Размер массива" + newTournamentTable.size());
        imageBitmap = activity.getImageArray();
        Log.i(TAG, "onCreateView: Создание таблицы");

        tableLay = view.findViewById(R.id.tableForDiv);
        tableLay.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.backTable));
        //textView = (TextView) view.findViewById(R.id.textView_test);
        //LinearLayout linearLayout = view.findViewById(R.id.layout_table);
        /*ViewGroup.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i = 0; i < newTournamentTable.size() ; i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setImageBitmap(newTournamentTable.get(i).getBitmapImageBig());
            imageView.setLayoutParams(imageParams);
            linearLayout.addView(imageView);
        }*/
        //image = view.findViewById(R.id.imageViewTest);
        //image.setImageBitmap(imageBitmap.get(0));
        if(newTournamentTable.size() != 0){
            update(PublicConstants.OPTION_UPDATE);
        }else {
            //textView.setText("Чисто проверить! ");
        }


        return view;
    }

/*
    public void createTable(){
        Log.i(TAG, "createTable: Создание таблицы");
        int countColumn = 10;
        Log.i(TAG, "createTable: размер = " + newTournamentTable.size());
        for(int i = 0; i < newTournamentTable.size(); i++){
            Log.i(TAG, "createTable: Строка = " + i);
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //tableRow.setBackgroundResource();
            for(int j = 0; j < countColumn; j++){
                Log.i(TAG, "createTable: Столбец = " + j);
                TextView tv = new TextView(getContext());
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                switch (j){
                    case 0: tv.setText(i+"1");break;
                    case 1:
                        ImageView imageView = new ImageView(getContext());
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(_50dp,_50dp,1.0f));
                        imageView.setImageBitmap(newTournamentTable.get(i).getBitmapImageBig());
                        tableRow.addView(tv, j);
                        break;
                    case 2: tv.setText(newTournamentTable.get(i).getTeamName());break;
                    case 3: tv.setText(String.valueOf(newTournamentTable.get(i).getGames()));break;
                    case 4: tv.setText(String.valueOf(newTournamentTable.get(i).getWins()));break;
                    case 5: tv.setText(String.valueOf(newTournamentTable.get(i).getDraws()));break;
                    case 6: tv.setText(String.valueOf(newTournamentTable.get(i).getLosses()));break;
                    case 7: tv.setText(String.valueOf(newTournamentTable.get(i).getGoalsScored()));break;
                    case 8: tv.setText(String.valueOf(newTournamentTable.get(i).getGoalsConceded()));break;
                    case 9: tv.setText(String.valueOf(newTournamentTable.get(i).getPoint()));break;
                }
                if(j!=1){
                    tableRow.addView(tv, j);
                }
            }
            table.addView(tableRow, i);
        }
    }
*/

    int changeColor(String nameDivision,int position){
        int color = 0;
        switch (nameDivision){
            case "Высший дивизион":
                if(position == 0 || position < 7){
                    color = green;
                }else{
                    color = red;
                }

                break;
            case "Первый дивизион":
                if (position < 4){
                    color = green;
                }else if(position == 4 || position == 5){
                    color = yellow;
                }else if (position > 15) {
                    color = red;
                }else{
                    color = whiteColor;
                }
                break;
                case "Второй дивизион A":
                case "Второй дивизион B":
                if (position < 4){
                    color = green;
                }else if(position > 17){
                    color = red;
                }else {
                    color = whiteColor;
                }
                break;
                case "Третий дивизион A":
                case "Третий дивизион B":
                if(position==0 || position==1){
                    color = green;
                }else{
                    color = whiteColor;
                }
                break;

        }
        color = whiteColor;
        return color;
    }

    @Override
    public void update(int option) {
        Log.i(TAG, "Interface: Сработал пустой метод");
        newTournamentTable = activity.getTournamentTable();
        imageBitmap = activity.getImageArray();
        Log.i(TAG, "createTable: Создание таблицы");
        int countColumn = 7;
        Log.i(TAG, "createTable: размер = " + newTournamentTable.size());
        tableLay.removeAllViews();



        for(int i = -1; i < newTournamentTable.size(); i++){
            //Log.i(TAG, "createTable: Строка = " + i);
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //tableRow.setBackgroundResource();
            for(int j = 0; j < countColumn; j++){
                if(i == -1){
                    //Log.i(TAG, "createTable: Столбец = " + j);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.gravity = Gravity.CENTER;
                    params.setMargins(_1dp, _1dp, _1dp, _1dp);
                    TextView tv = new TextView(getActivity());
                    tv.setLayoutParams(params);
                    tv.setGravity(Gravity.CENTER);
                    tv.setBackgroundColor(whiteColor);
                    switch (j){
                        case 0: tv.setText("#");break;
                        case 1: tv.setText("Лого");break;
                        case 2: tv.setText("Команда");break;
                        case 3: tv.setText("И");break;
                        case 4: tv.setText("З");break;
                        case 5: tv.setText("П");break;
                        case 6: tv.setText("О");break;
                    }
                    tableRow.addView(tv, j);
                }else{
                    //Log.i(TAG, "createTable: Столбец = " + j);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    //params.gravity = Gravity.CENTER;
                    final TextView tv = new TextView(getActivity());
                    params.setMargins(_1dp, _1dp, _1dp, _1dp);
                    tv.setLayoutParams(params);
                    tv.setGravity(Gravity.CENTER);
                    tv.setBackgroundColor(changeColor(newTournamentTable.get(i).getDivisionName(), i));
                    switch (j){
                        case 0: tv.setText(String.valueOf(i+1));break;
                        case 1:
                            TableRow.LayoutParams paramsImage = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                            //paramsImage.gravity = Gravity.CENTER;
                            paramsImage.setMargins(_1dp, _1dp, _1dp, _1dp);
                            ImageView imageView = new ImageView(getActivity());
                            imageView.setBackgroundColor(changeColor(newTournamentTable.get(i).getDivisionName(), i));
                            imageView.setLayoutParams(paramsImage);
                            byte[] decodedBytes = Base64.decode(newTournamentTable.get(i).getImageBase64(), Base64.DEFAULT);
                            Bitmap decodedBitmap = BitmapFactory.decodeByteArray (decodedBytes, 0, decodedBytes.length);
                            imageView.setImageBitmap(decodedBitmap);
                            tableRow.addView(imageView, j);
                            break;
                        case 2:
                            tv.setText(newTournamentTable.get(i).getTeamName());
                            final int finalI = i;
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), TeamActivity.class);
                                    intent.putExtra("dateForActivity", newTournamentTable.get(finalI).getImageBase64());
                                    /*ImageFromServer descriptionImage = null;
                                    if(imageBitmap != null){
                                        descriptionImage = imageBitmap.get(finalI);
                                    }
                                    if(descriptionImage != null){
                                        intent.putExtra("dateForActivity", (Parcelable) descriptionImage);
                                    }*/
                                    intent.putExtra("dataForActivityName", tv.getText());
                                    intent.putExtra("dataForActivityID", newTournamentTable.get(finalI).getIdTeam());
                                    startActivity(intent);
                                }
                            });
                            break;
                        case 3: tv.setText(newTournamentTable.get(i).getGames());break;
                        case 4: tv.setText(newTournamentTable.get(i).getGoalScored());break;
                        case 5: tv.setText(newTournamentTable.get(i).getGoalConceded());break;
                        case 6: tv.setText(newTournamentTable.get(i).getPoint());break;
                    }
                    //Log.i(TAG, "onCreateView: TV = " + tv.getText());
                    if(j!=1){
                        tableRow.addView(tv, j);
                    }
                }
            }
            tableLay.addView(tableRow, i+1);
        }
        /*Log.i(TAG, "Interface: " + newTournamentTable.size());
        String results = "";
        for(int i = 0; i < newTournamentTable.size(); i++){
            results += newTournamentTable.get(i).toString();
        }
        textView.setText(results);*/
    }


    private Bitmap teamPicture(String nameTeam){

        for(int i = 0; i < imageBitmap.size(); i++){
            if(nameTeam.equals(imageBitmap.get(i).getNameImage())){
                return imageBitmap.get(i).getBitmapImageBig();
            }
        }

        return null;
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
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");
    }


}
