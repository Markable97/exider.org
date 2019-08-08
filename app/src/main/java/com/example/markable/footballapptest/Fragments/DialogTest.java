package com.example.markable.footballapptest.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markable.footballapptest.Adapters.RecyclerViewAddMatches;
import com.example.markable.footballapptest.AddMatchActivity;
import com.example.markable.footballapptest.Classes.Schedule;
import com.example.markable.footballapptest.Classes.Stadiums;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;

public class DialogTest extends DialogFragment implements DialogInterface.OnClickListener {
    LinearLayout layoutMain;
    Context context;
    ArrayList<View> listView = new ArrayList<>();
    ArrayList<Stadiums> stadiumsList = new ArrayList<>();
    ArrayList<Schedule> scheduleList = new ArrayList<>();
    String[] data = {"10:20","11:50","12:40","13:20","16:00","20:20","17:10","18:40","19:50"};
    String test = "Что-то";
    final String TAG_DIALOG = "dialog_add_time_match";
    AddMatchActivity activity;
    /*public static DialogTest newInstance(ArrayList<Stadiums> stadiums, ArrayList<Schedule> schedule){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList();
        DialogTest dialogTest = new DialogTest();
        return dialogTest;
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        context = getActivity();
        activity = (AddMatchActivity)getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_add_match_time, null);
        layoutMain = (LinearLayout) view.findViewById(R.id.liner_dialog_add_match);
        layoutMain.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        stadiumsList = activity.getStadiumsList();
        scheduleList = activity.getScheduleList();
        int k = 0;
        int k_main = 0;
        for(int i = 0; i < stadiumsList.size(); i++){
            TextView textView = new TextView(getActivity());
            final int pole = i+1;
            textView.setText(stadiumsList.get(i).getNameStadium());
            TableLayout tableLayout = new TableLayout(getActivity());
            /*tableLayout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));*/
            tableLayout.setPadding(10,10,10,10);
            ArrayList<Schedule> time = getTime(stadiumsList.get(i).getNameStadium());
            //tableLayout.setGravity(Gravity.CENTER);
            for(int row = 0; row <= time.size() % 4 + 1; row++ ){
                TableRow tableRow = new TableRow(getActivity());
                tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(5, 5, 5, 5);
                for(int colum = 0; colum < 4; colum++){
                    if(k != time.size()){
                        final Button tv = new Button(getActivity());
                        tv.setLayoutParams(params);
                        if (time.get(k).getChecked() == 1){
                            tv.setEnabled(false);
                            tv.setBackgroundColor(Color.parseColor("#FFFFEBEE"));
                        }else{
                            tv.setBackgroundColor(Color.parseColor("#FFE8F5E9")); //FFFFEBEE
                        }
                        tv.setText(time.get(k).getTime());
                        k++;
                        scheduleList.get(k_main).setView(tv);
                        listView.add(tv);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tv.setEnabled(false);
                                tv.setBackgroundColor(Color.parseColor("#FFFFEBEE"));
                                btnActiv(tv);
                                test = "Спартак - " +pole +"\nВремя "+tv.getText();
                                //Toast.makeText(getContext(),"Спартак - " +pole +"\nВремя "+tv.getText(), Toast.LENGTH_LONG).show();
                            }
                        });
                        k_main++;
                        tableRow.addView(tv);
                    }
                }
                tableLayout.addView(tableRow);
            }
            /*GridView gridView = new GridView(getActivity());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
            gridView.setAdapter(adapter);
            gridView.setNumColumns(3);*/
            layoutMain.addView(textView);
            layoutMain.addView(tableLayout);
            k=0;
            //layoutMain.addView(gridView);
        }

        return builder.setTitle("Выберите расписания матча")
                .setView(view)
                .setPositiveButton("OK", this)
                .setNegativeButton("Отмена", this)
                .create();

    }

    private ArrayList<Schedule> getTime(String nameStadium) {
        ArrayList<Schedule> time = new ArrayList<>();
        for(Schedule s : scheduleList){
            if(s.getStadium().getNameStadium().equals(nameStadium)){
                time.add(new Schedule(s.getTime(), s.getChecked()));
            }
        }
        return time;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i){
            case Dialog.BUTTON_POSITIVE:
                ((AddMatchActivity)getActivity()).test(test);
                break;
            case Dialog.BUTTON_NEGATIVE:
                dismiss();
                break;
        }
    }

    void btnActiv(View btnActiv){
        for(int i = 0; i < scheduleList.size(); i++){
            if(!btnActiv.equals(scheduleList.get(i).getView()) && scheduleList.get(i).getChecked()!= 1){
                Button btn = (Button) listView.get(i);
                btn.setBackgroundColor(Color.parseColor("#FFE8F5E9"));
                btn.setEnabled(true);
            }
        }
    }
}
