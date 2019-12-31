package com.example.markable.footballapptest.Adapters;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markable.footballapptest.Classes.ImageFromServer;
import com.example.markable.footballapptest.Classes.NextMatches;
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
        return new ViewHolder(view);
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
        if(match.getPlayed() == 1){
            holder.bind(true);
           holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    PrevMatches matches = list.get(position);
                    Intent intent = new Intent(holder.itemView.getContext(), MatchActivity.class);
                    /*Log.i(TAG, "onClick: Картинки" + listImage.size());
                    for(int i = 0; i < listImage.size(); i++){
                        if(matches.getTeamHome().equalsIgnoreCase(listImage.get(i).getNameImage())){
                            Log.i(TAG, "onClick: Зашел в If для дома");
                            imageHome = listImage.get(i);
                        }
                        if(matches.getTeamVisit().equalsIgnoreCase(listImage.get(i).getNameImage())){
                            Log.i(TAG, "onClick: Зашел в If для гостей");
                            imageVisit = listImage.get(i);
                        }
                    }
                    Log.i(TAG, "onClick: " + imageHome.getNameImage() + imageVisit.getNameImage());*/
                    intent.putExtra("information", matches);
                    //intent.putExtra("imageHome", (Parcelable) imageHome);
                    //intent.putExtra("imageVisit", (Parcelable) imageVisit);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }else{
            holder.bind(false);
        }
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
        TextView tour;
        ImageView imageTeamHome;
        ImageView imageTeamVisit;
        TextView nameTeamHome;
        TextView nameTeamVisit;
        TextView goalTeamHome;
        TextView goalTeamVisit;

        ViewHolder(View itemView) {
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
                    if(!played){
                        onFalsePlaedListiner.playedFalse();
                    }
                }
            });
        }
        public void bind(boolean played){
            this.played = played;
        }
    }
}
