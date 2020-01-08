package com.example.markable.footballapptest.Adapters;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.NextMatches;
import com.example.markable.footballapptest.Classes.Schedule;
import com.example.markable.footballapptest.Classes.Stadiums;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;


public class RecyclerViewAddMatches extends RecyclerView.Adapter<RecyclerViewAddMatches.ViewHolder> {

    ArrayList<NextMatches> list;
    FragmentManager manager;
    ArrayList<Stadiums> stadiumsList = new ArrayList<>();
    ArrayList<Schedule> scheduleList = new ArrayList<>();
    private OnAddMatchClickListener onAddMatchClickListener;

    public RecyclerViewAddMatches(ArrayList<NextMatches> gamesInTour, OnAddMatchClickListener listener/*, FragmentManager manager*/) {
        this.list = gamesInTour;
        this.onAddMatchClickListener = listener;
        //this.manager = manager;
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
            holder.date.setText(match.getDate());
            holder.stadium.setText(match.getNameStadium());
            holder.refere.setText("Судья матча: ");
            holder.bind(match);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(ArrayList<NextMatches> gamesInTour, ArrayList<Schedule> scheduleList, ArrayList<Stadiums> stadiumsList ){
        this.list = gamesInTour;
        this.scheduleList = scheduleList;
        this.stadiumsList = stadiumsList;
        notifyDataSetChanged();
    }

    public void update(ArrayList<NextMatches> gamesInTour){
        this.list =  gamesInTour;
        notifyDataSetChanged();
    }

    public void update(ArrayList<NextMatches> matches, ArrayList<Schedule> schedule){
        this.list = matches;
        this.scheduleList = schedule;
        notifyDataSetChanged();
    }

    public interface OnAddMatchClickListener{
        void onMatchClick(NextMatches match, int check);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tour;
        TextView nameHome;
        TextView nameVisit;
        TextView date;
        TextView stadium;
        TextView refere;

        NextMatches match;

        public ViewHolder(final View itemView) {
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
                    if(stadium.getText().length()==0){
                        match = list.get(getLayoutPosition());
                        onAddMatchClickListener.onMatchClick(match, 1);
                    }else{
                        match = list.get(getLayoutPosition());
                        onAddMatchClickListener.onMatchClick(match, 0);
                    }
                }
            });

        }
        public void bind(NextMatches matchClick){
            this.match = matchClick;
        }
    }
}
