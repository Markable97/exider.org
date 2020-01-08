package com.example.markable.footballapptest.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markable.footballapptest.AddMatchActivity;
import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.Schedule;
import com.example.markable.footballapptest.Classes.Stadiums;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;

public class DialogTest extends DialogFragment implements DialogInterface.OnClickListener {
    private static final String TAG = DialogTest.class.getSimpleName();
    LinearLayout layoutMain;
    Context context;
    ArrayList<View> listView = new ArrayList<>();
    ArrayList<Stadiums> stadiumsList = new ArrayList<>();
    ArrayList<Schedule> scheduleList = new ArrayList<>();
    //String[] data = {"10:20","11:50","12:40","13:20","16:00","20:20","17:10","18:40","19:50"};
    Schedule returnDialog;
    NextMatches matchForSchedule;
    String test = "Что-то";
    final String TAG_DIALOG = "dialog_add_time_match";
    AddMatchActivity activity;

    int buzyColor;
    int freeColor;
    int activeColor;
    /*public static DialogTest newInstance(ArrayList<Stadiums> stadiums, ArrayList<Schedule> schedule){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList();
        DialogTest dialogTest = new DialogTest();
        return dialogTest;
    }*/
    public static DialogTest newInstance(NextMatches match){
        Bundle args = new Bundle();
        args.putSerializable("match_schedule", match);
        DialogTest fragment = new DialogTest();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        matchForSchedule = (NextMatches) getArguments().getSerializable("match_schedule");
        context = getActivity();
        activity = (AddMatchActivity)getActivity();
        buzyColor = activity.getResources().getColor(R.color.buzy_schedule);
        freeColor = activity.getResources().getColor(R.color.free_schedule);
        activeColor = activity.getResources().getColor(R.color.active_schedule);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_add_match_time, null);
        layoutMain = (LinearLayout) view.findViewById(R.id.liner_dialog_add_match);
        layoutMain.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        stadiumsList = activity.getStadiumsList();
        scheduleList = activity.getScheduleList();
        int k = 0;
        for(int i = 0; i < stadiumsList.size(); i++){
            final TextView textView = new TextView(getActivity());
            final int pole = i+1;
            textView.setText(stadiumsList.get(i).getNameStadium());
            TableLayout tableLayout = new TableLayout(getActivity());
            tableLayout.setPadding(10,10,10,10);
            final ArrayList<Schedule> time = getTime(stadiumsList.get(i).getNameStadium());
            Log.i(TAG, "Для поля " + i + "Расписание: \n" + time);
            for(int row = 0; row <= time.size() / 4; row++ ){
                TableRow tableRow = new TableRow(getActivity());
                tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, 1);
                TableRow.LayoutParams params2 = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(5, 5, 5, 5);
                params2.setMargins(5, 5, 5, 5);
                for(int colum = 0; colum < 4; colum++){
                    if(k != time.size()){
                        final Schedule s = time.get(k);
                        final Button tv = new Button(getActivity());
                        tv.setLayoutParams(params);
                        /*if(time.size()-k+1 < 3 && colum!=3 ){
                        }else{
                            tv.setLayoutParams(params);
                        }*/
                        if (time.get(k).getBusy_time() == 1){
                            //tv.setEnabled(false);
                            tv.setBackgroundColor(buzyColor);//(Color.parseColor("#FFFFEBEE"));
                        }else{
                            tv.setBackgroundColor(freeColor);//(Color.parseColor("#FFE8F5E9")); //FFFFEBEE
                        }
                        //tv.setTextColor(Color.GRAY);
                        tv.setText(time.get(k).getMatch_time());
                        addSchedule(tv, time.get(k));
                        k++;
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(s.getBusy_time()==1){
                                    returnDialog = null;
                                    Toast.makeText(context, "Время занято!\n" +
                                            "Играют: " +s.getName_division() + " тур - " +
                                            s.getId_tour() + "\n" +
                                            s.getTeam_home() + " - " + s.getTeam_guest(),Toast.LENGTH_LONG).show();
                                }else{
                                    tv.setEnabled(false);
                                    tv.setBackgroundColor(activeColor);//(Color.parseColor("#FFE8EAF6"));
                                    returnDialog = s;
                                    btnActiv(tv);
                                    test = "Спартак - " +pole +"\nВремя "+tv.getText();
                                }
                                //Toast.makeText(getContext(),"Спартак - " +pole +"\nВремя "+tv.getText(), Toast.LENGTH_LONG).show();
                            }
                        });
                        tableRow.addView(tv);
                    }else{
                        Button dummy = new Button(getActivity());
                        dummy.setLayoutParams(params);
                        dummy.setVisibility(View.INVISIBLE);
                        tableRow.addView(dummy);
                    }
                }
                tableLayout.addView(tableRow);
            }
            layoutMain.addView(textView);
            layoutMain.addView(tableLayout);
            k=0;
        }
        Log.i(TAG, "Расписание с кнпками: \n" + scheduleList.toString());
        return builder.setTitle("Выберите расписания матча")
                .setView(view)
                .setPositiveButton("OK", this)
                .setNegativeButton("Отмена", this)
                .create();

    }

    private ArrayList<Schedule> getTime(String nameStadium) {
        ArrayList<Schedule> time = new ArrayList<>();
        for(Schedule s : scheduleList){
            if(s.getName_stadium().equals(nameStadium)){
                time.add(s);
            }
        }
        return time;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i){
            case Dialog.BUTTON_POSITIVE:
                if(returnDialog != null){
                    returnDialog.setId_match(matchForSchedule.getIdMatch());
                    returnDialog.setId_tour(matchForSchedule.getIdTour());
                    returnDialog.setId_division(matchForSchedule.getIdDivision());
                    returnDialog.setName_division(matchForSchedule.getNameDivision());
                    returnDialog.setTeam_home(matchForSchedule.getTeamHome());
                    returnDialog.setTeam_guest(matchForSchedule.getTeamVisit());
                    returnDialog.setBusy_time(1);
                }
                ((AddMatchActivity)getActivity()).clickSchedule(returnDialog);
                break;
            case Dialog.BUTTON_NEGATIVE:
                dismiss();
                break;
        }
    }
    void addSchedule(View view, Schedule s){
        for(Schedule e : scheduleList){
            if(e.getMatch_time().equals(s.getMatch_time())&&e.getId_stadium() == s.getId_stadium() ){
                Button b = (Button) view;
                Log.i(TAG, "Нажата кнопка " + b.getText() + "поместитьь в " + e.getMatch_time());
                e.setView(view);
            }
        }
    }
    void btnActiv(View btnActiv){
        Button b = (Button) btnActiv;
        Log.i(TAG, "Время кнопки" + b.getText());
        for(Schedule s : scheduleList){
            if(!s.getView().equals(btnActiv) && s.getBusy_time()!=1){
                Button btn = (Button)s.getView();
                btn.setBackgroundColor(freeColor);//(Color.parseColor("#FFE8F5E9"));
                btn.setEnabled(true);
            }
        }
    }
}
