package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Beast;
import com.example.project.Model.CreateBeast;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder> {

    private Context lContext;
    private List<CreateBeast> beastList = new ArrayList<>();

    public StatisticsAdapter(Context lContext, List<CreateBeast> Beasts) {
        this.lContext = lContext;
        this.beastList = Beasts;
    }

    @NonNull
    @Override
    public StatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_statics,parent,false);
        return new StatisticsAdapter.StatisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsViewHolder holder, int position) {
        CreateBeast beastItem = beastList.get(position);
        Beast beast = beastItem.getBeast();

        holder.name.setText(beastItem.getCustomName());
        holder.ele.setText(beast.getEle());
        holder.wins.setText(String.valueOf(beast.getWins()));
        holder.lost.setText(String.valueOf((beast.getLosses())));
        holder.battle.setText(String.valueOf(beast.getMatchCount()));
        holder.train.setText(String.valueOf(beast.getTrainCount()));
        holder.imageid.setImageResource(beastItem.getBeast().getImageResource());
    }

    @Override
    public int getItemCount() {
        return beastList.size();
    }

    public class StatisticsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageid;

        TextView name, ele, wins, train, lost, battle;

        public StatisticsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageid = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.name);
            ele = itemView.findViewById(R.id.element);
            wins = itemView.findViewById(R.id.win);
            train = itemView.findViewById(R.id.train);
            lost = itemView.findViewById(R.id.lost);
            battle = itemView.findViewById(R.id.battle);
        }
    }
}
