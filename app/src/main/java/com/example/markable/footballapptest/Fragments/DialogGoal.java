package com.example.markable.footballapptest.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.markable.footballapptest.Classes.Player;
import com.example.markable.footballapptest.R;

public class DialogGoal extends DialogFragment implements DialogInterface.OnClickListener{

    private static final String TAG = DialogGoal.class.getSimpleName();

    public static final String TAG_GOAL = "goal";
    public static final String TAG_PENALTY = "penalty";
    public static final String TAG_PENALTY_OUT = "penalty_out";
    public static final String TAG_GOAL_OWN = "goal_own";
    public static final String TAG_PLAYER = "id_player";

    int idPlayer;

    Player player;

    EditText goal, penalty, penaltyOut, ownGoal;

    public static DialogGoal newInstance(Player player){
        Bundle args = new Bundle();
        args.putSerializable("player_goal", player);
        DialogGoal fragment = new DialogGoal();
        fragment.setArguments(args);
        return fragment;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i(TAG, "Создание диалога");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_goal, null);

        player = (Player) getArguments().getSerializable("player_goal");

        goal = view.findViewById(R.id.dialogGoal_goal);
        penalty = view.findViewById(R.id.dialogGoal_penalty);
        penaltyOut = view.findViewById(R.id.dialogGoal_penaltyOut);
        ownGoal = view.findViewById(R.id.dialogGoal_ownGoal);

        if (player.getGoal()>0)
            goal.setText(String.valueOf(player.getGoal()));
        if (player.getPenalty()>0)
            penalty.setText(String.valueOf(player.getPenalty()));
        if (player.getPenalty_out()>0)
            penaltyOut.setText(String.valueOf(player.getPenalty_out()));
        if (player.getOwn_goal()>0)
            ownGoal.setText(String.valueOf(player.getOwn_goal()));

        return builder
                .setView(view)
                .setPositiveButton("OK", this)
                .setNegativeButton("Отмена", this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i){
            case Dialog.BUTTON_NEGATIVE:
                dismiss();
                break;
            case Dialog.BUTTON_POSITIVE:
                Intent intent = new Intent();
                intent.putExtra(TAG_PLAYER, player.getIdPlayer());
                if(goal.getText().length()>0){
                    intent.putExtra(TAG_GOAL, Integer.parseInt(goal.getText().toString()));
                }
                if(ownGoal.getText().length()>0){
                    intent.putExtra(TAG_GOAL_OWN, Integer.parseInt(ownGoal.getText().toString()));
                }
                if(penalty.getText().length()>0){
                    intent.putExtra(TAG_PENALTY, Integer.parseInt(penalty.getText().toString()));
                }
                if(penaltyOut.getText().length()>0){
                    intent.putExtra(TAG_PENALTY_OUT, Integer.parseInt(penaltyOut.getText().toString()));
                }

                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                break;
        }
    }
}
