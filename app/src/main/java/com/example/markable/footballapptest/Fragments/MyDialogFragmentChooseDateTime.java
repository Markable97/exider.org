package com.example.markable.footballapptest.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.markable.footballapptest.MainActivity;
import com.example.markable.footballapptest.R;

import java.util.Calendar;

public class MyDialogFragmentChooseDateTime extends AppCompatDialogFragment implements View.OnTouchListener, View.OnClickListener {

    final String TAG_DIALOG = "dialog_choose";

    DatePickerDialog beginPicker;
    DatePickerDialog finishPicker;
    TimePickerDialog time_s1, time_po1, time_s2, time_po2;

    Button bt_sent, bt_cancel;

    EditText editText_date1, editText_date2, editText_time_s1, editText_time_s2, editText_time_po1, editText_time_po2;
    private int mYear, mMonth, mDay, mHour, mMinute;


    DatePickerDialog.OnDateSetListener beginDateLister = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String monthNew = "", dayNew = "";
            if (month + 1 < 10){
                monthNew = "0" + (month + 1);
            }
            if (dayOfMonth < 10){
                dayNew = "0" + dayOfMonth;
            }
            String str = dayNew + "." + monthNew + "." + year;
            editText_date1.setText(str);
        }
    };
    TimePickerDialog.OnTimeSetListener timeListenerS1 = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String str = hourOfDay + " : " + minute;
            editText_time_s1.setText(str);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.fragment_dialog_choose_date_time, null);
        //builder.setView(view);
        editText_date1 = view.findViewById(R.id.editText_date1);
        editText_date1.setKeyListener(null);
        editText_date1.setOnTouchListener(this);
        editText_time_s1 = view.findViewById(R.id.editText_time_s1);
        editText_time_s1.setKeyListener(null);
        editText_time_s1.setOnTouchListener(this);
        editText_time_po1 = view.findViewById(R.id.editText_time_po1);
        editText_time_po1.setKeyListener(null);
        editText_time_po1.setOnTouchListener(this);
        editText_date2 = view.findViewById(R.id.editText_date1);
        editText_date2.setKeyListener(null);
        editText_date2.setOnTouchListener(this);
        editText_time_s2 = view.findViewById(R.id.editText_time_s1);
        editText_time_s2.setKeyListener(null);
        editText_time_s2.setOnTouchListener(this);
        editText_time_po2 = view.findViewById(R.id.editText_time_po1);
        editText_time_po2.setKeyListener(null);
        editText_time_po2.setOnTouchListener(this);
        bt_sent = view.findViewById(R.id.bt_sent);
        bt_sent.setOnClickListener(this);
        bt_cancel = view.findViewById(R.id.bt_cancel_dialog);
        bt_cancel.setOnClickListener(this);
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);
        beginPicker = new DatePickerDialog(getActivity(), beginDateLister, mYear, mMonth, mDay );
        //finishPicker = new DatePickerDialog(getActivity(), finishDateLister, mYear, mMonth, mDay );
        cal.set(2019,4,9);
        //beginPicker.getDatePicker().setMinDate(cal.getTimeInMillis());;
        time_s1 = new TimePickerDialog(getActivity(), timeListenerS1 ,mHour, mMinute, true);
        // Остальной код
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){
            case R.id.editText_date1:
                beginPicker.show();
                break;
            case R.id.editText_time_s1:
                time_s1.show();
                break;
            case R.id.editText_time_po1:
                time_s1.show();
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bt_sent:
                ((MainActivity)getActivity()).okClicked(TAG_DIALOG);
                dismiss();
                break;
            case R.id.bt_cancel_dialog:
                dismiss();
                break;
        }

    }

    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выбор желаемых дат");
        return builder.create();
    }*/

}
