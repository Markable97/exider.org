package com.example.markable.footballapptest.Adapters;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Fragments.DialogTest;
import com.example.markable.footballapptest.Fragments.MyDialogFragment;
import com.example.markable.footballapptest.Fragments.MyDialogFragmentAddMatchTime;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;


public class RecyclerViewAddMatches extends RecyclerView.Adapter<RecyclerViewAddMatches.ViewHolder> {

    ArrayList<NextMatches> list;
    FragmentManager manager;

    public RecyclerViewAddMatches(ArrayList<NextMatches> gamesInTour, FragmentManager manager) {
        this.list = gamesInTour;
        this.manager = manager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_add_match,parent,false);
        return new RecyclerViewAddMatches.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(!list.isEmpty()){
            NextMatches match = list.get(position);
            holder.tour.setText("Тур " + match.getIdTour());
            holder.nameHome.setText(match.getTeamHome());
            holder.nameVisit.setText(match.getTeamVisit());
            holder.date.setText("");
            holder.stadium.setText("");
            holder.refere.setText("Судья матча: ");

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(ArrayList<NextMatches> gamesInTour){
        this.list = gamesInTour;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tour;
        TextView nameHome;
        TextView nameVisit;
        TextView date;
        TextView stadium;
        TextView refere;

        public ViewHolder(View itemView) {
            super(itemView);
            tour = (TextView) itemView.findViewById(R.id.addMatchTour);
            nameHome = (TextView) itemView.findViewById(R.id.addMatchHome);
            nameVisit = (TextView) itemView.findViewById(R.id.addMatchVisit);
            date = (TextView) itemView.findViewById(R.id.addMatchDate);
            stadium = (TextView) itemView.findViewById(R.id.addMatchStadium);
            refere = (TextView) itemView.findViewById(R.id.addMatchRefere);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //MyDialogFragmentAddMatchTime dialog = new MyDialogFragmentAddMatchTime();
                    DialogTest test = new DialogTest();
                    FragmentTransaction transaction = manager.beginTransaction();
                    //dialog.show(transaction, "dlgAddMatch");
                    test.show(transaction, "dlgAddMatch");
                }
            });
        }
    }
}
