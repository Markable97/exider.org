package com.example.markable.footballapptest.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.markable.footballapptest.R;

public class MyDialogFragmentAddMatchTime extends AppCompatDialogFragment implements DialogInterface.OnClickListener {

    LinearLayout layoutMain;

    String[] data = {"10:20","11:50","12:40","13:20","16:00","20:20","17:10","18:40","19:50"};

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        //getDialog().setTitle("Выберите время матча");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("NO",  this)
                .setPositiveButton("Yes",  this);
        //AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view = inflater.inflate(R.layout.fragment_dialog_add_match_time, null);
        layoutMain = (LinearLayout) view.findViewById(R.id.liner_dialog_add_match);
        layoutMain.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        int k = 0;
        for(int i = 0; i < 4; i++){
            TextView textView = new TextView(getActivity());
            textView.setText("Спартак - " + String.valueOf(i+1));
            TableLayout tableLayout = new TableLayout(getActivity());
            for(int row = 0; row <= data.length / 3; row++ ){
                TableRow tableRow = new TableRow(getActivity());
                tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(5, 5, 5, 5);
                for(int colum = 0; colum < 3; colum++){
                    if(k != data.length){
                        Button tv = new Button(getActivity());
                        tv.setLayoutParams(params);
                        tv.setText(data[k]);
                        k++;
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
        builder.create();
        return view;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
