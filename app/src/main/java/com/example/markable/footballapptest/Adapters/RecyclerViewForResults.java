package com.example.markable.footballapptest.Adapters;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.MatchActivity;
import com.example.markable.footballapptest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс адаптера наследуется от RecyclerView.Adapter с указанием класса, который будет хранить ссылки на виджеты элемента списка,
 * т.е. класса, имплементирующего ViewHolder. В нашем случае класс объявлен внутри класса адаптера.
 */

public class RecyclerViewForResults extends RecyclerView.Adapter<RecyclerViewForResults.ViewHolder> {

    private static final String TAG = "AdapterResults";
    PrevMatches match;
    private List<PrevMatches>  list;
    private OnFalsePlayedListiner onFalsePlaedListiner;
    //конструктов для адаптера
    public RecyclerViewForResults(/*Context context,*/ List<PrevMatches> list){
        this.list = list;
    }
    public RecyclerViewForResults(/*Context context,*/ List<PrevMatches> list, OnFalsePlayedListiner listener ){
        this.list = list;
        this.onFalsePlaedListiner = listener;
    }
    @Override
    public RecyclerViewForResults.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_results, parent, false);
        return new ViewHolder(view, this.list);
    }

    @Override
    public boolean onFailedToRecycleView(ViewHolder holder) {
        return true;
    }
    @Override
    public void onBindViewHolder(final RecyclerViewForResults.ViewHolder holder, final int position) {
        match = list.get(position);

        holder.tour.setText("Тур " + String.valueOf(match.getIdTour()));
        if(!match.getImageHome().isEmpty()){
            try{
                byte[] decodedBytes = Base64.decode(match.getImageHome(), Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray (decodedBytes, 0, decodedBytes.length);
                holder.imageTeamHome.setImageBitmap(decodedBitmap);
            }catch (Exception e){
                System.out.println(match.getTeamHome() + "\n" + match.getImageHome());
            }
        }
        if(!match.getImageGuest().isEmpty()){
            try {
                byte[] decodedBytes = Base64.decode(match.getImageGuest(), Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray (decodedBytes, 0, decodedBytes.length);
                holder.imageTeamVisit.setImageBitmap(decodedBitmap);
            }catch (Exception e){
                System.out.println("Bad base-64: " + match.getTeamHome() + "\n" + match.getImageHome());
            }
        }
        /*if(match.getImageHomeImage()!=null){
            holder.imageTeamHome.setImageBitmap(match.getImageHomeImage().getBitmapImageBig());
        }
        if(match.getImageVisitImage()!=null){
            holder.imageTeamVisit.setImageBitmap(match.getImageVisitImage().getBitmapImageBig());
        }*/
        holder.nameTeamHome.setText(match.getTeamHome());
        holder.nameTeamVisit.setText(match.getTeamVisit());
        if(match.getPlayed() == 1) {
            holder.goalTeamHome.setText(String.valueOf(match.getGoalHome()));
            holder.goalTeamVisit.setText(String.valueOf(match.getGoalVisit()));
        }else{
            holder.goalTeamHome.setText("");
            holder.goalTeamVisit.setText("");
        }


        /*holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(match.getPlayed()==1){
                    holder.bind(true, match);
                }else{
                    holder.bind(false, match);
                }
            }
        });*/



    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(ArrayList<PrevMatches> prevMatches, ArrayList<ImageFromServer> image){
        this.list = prevMatches;
        notifyDataSetChanged();
    }
    public interface OnFalsePlayedListiner{
        void playedFalse();
    }
    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        Boolean played;
        PrevMatches match;
        TextView tour;
        ImageView imageTeamHome;
        ImageView imageTeamVisit;
        TextView nameTeamHome;
        TextView nameTeamVisit;
        TextView goalTeamHome;
        TextView goalTeamVisit;

        ViewHolder(final View itemView, final List<PrevMatches> matches) {
            super(itemView);
            tour = itemView.findViewById(R.id.res_tour);
            imageTeamHome = itemView.findViewById(R.id.res_image_home);
            imageTeamVisit = itemView.findViewById(R.id.res_image_visit);
            nameTeamHome = itemView.findViewById(R.id.res_name_home);
            nameTeamVisit = itemView.findViewById(R.id.res_name_visit);
            goalTeamHome =  itemView.findViewById(R.id.res_goal_home);
            goalTeamVisit =  itemView.findViewById(R.id.res_goal_visit);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    match = matches.get(getLayoutPosition());
                    if(match.getPlayed()!=1){
                        onFalsePlaedListiner.playedFalse();
                    }else{
                        Intent intent = new Intent(itemView.getContext(), MatchActivity.class);
                        intent.putExtra("information", match);
                        itemView.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}
