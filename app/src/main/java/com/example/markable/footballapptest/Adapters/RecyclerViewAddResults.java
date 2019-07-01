package com.example.markable.footballapptest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markable.footballapptest.Classes.PrevMatches;
import com.example.markable.footballapptest.R;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAddResults extends RecyclerView.Adapter<RecyclerViewAddResults.ViewHolder> {

    List<PrevMatches> matches;
    private Context context;

    public RecyclerViewAddResults(Context context, List<PrevMatches> list) {
        this.matches = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row_add_results,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PrevMatches match = matches.get(position);
        holder.tour.setText("Тур" + String.valueOf(match.getIdTour()));
        holder.nameHome.setText(match.getTeamHome());
        holder.nameVisit.setText(match.getTeamVisit());
        holder.btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalHome = holder.goalHome.getText().toString();
                String goalVisit = holder.goalVisit.getText().toString();
                if (!goalHome.isEmpty() && !goalVisit.isEmpty()){
                    Toast.makeText(context,"Результат отправлен", Toast.LENGTH_LONG).show();
                    holder.goalHome.setEnabled(false);
                    holder.goalVisit.setEnabled(false);
                    holder.layoutMain.setBackgroundColor(context.getResources().getColor(R.color.input_register));
                    holder.btnSent.setEnabled(false);
                }else{
                    Toast.makeText(context,"Поля не заполнены", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameHome;
        TextView nameVisit;
        TextView tour;
        EditText goalHome;
        EditText goalVisit;
        Button btnSent;
        LinearLayout layoutMain;

        public ViewHolder(View itemView) {
            super(itemView);
            nameHome = (TextView) itemView.findViewById(R.id.res_add_name_home);
            nameVisit = (TextView) itemView.findViewById(R.id.res_add_name_visit);
            tour = (TextView) itemView.findViewById(R.id.res_add_tour);
            goalHome = (EditText) itemView.findViewById(R.id.res_add_goal_home);
            goalVisit = (EditText) itemView.findViewById(R.id.res_add_goal_visit);
            btnSent = (Button) itemView.findViewById(R.id.res_add_btn_sent);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.res_add_lay_main);
        }
    }
}
