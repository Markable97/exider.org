package com.example.markable.footballapptest.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.markable.footballapptest.MainActivity;
import com.example.markable.footballapptest.R;

import java.util.Calendar;

public class MyDialogFragmentChooseDateTime extends AppCompatDialogFragment {


    DatePickerDialog beginPicker;
    DatePickerDialog finishPicker;

    Button bt_date;
    EditText editText_date;
    private int mYear, mMonth, mDay, mHour, mMinute;


    DatePickerDialog.OnDateSetListener beginDateLister = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String str = dayOfMonth + "." + (month + 1) + "." + year;
            editText_date.setText(str);
        }
    };

  /*  @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dialog_choose_date_time, null);

        editText_date = view.findViewById(R.id.editText_date);
        bt_date = view.findViewById(R.id.bt_date);

        /*bt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);

                // инициализируем диалог выбора даты текущими значениями
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                                editText_date.setText(editTextDateParam);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        return view;
    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_choose_date_time, null);
        builder.setView(view);
        bt_date = view.findViewById(R.id.bt_date);
        editText_date = view.findViewById(R.id.editText_date);
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        beginPicker = new DatePickerDialog(getActivity(), beginDateLister, mYear, mMonth, mDay );
        //finishPicker = new DatePickerDialog(getActivity(), finishDateLister, mYear, mMonth, mDay );

        bt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginPicker.show();
            }
        });
        // Остальной код
        return builder.create();
    }

}
