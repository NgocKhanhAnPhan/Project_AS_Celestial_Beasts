package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.CreateBeast;
import com.example.project.R;
import com.example.project.View.Battle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BattleAdapter extends RecyclerView.Adapter<BattleAdapter.BattleViewHolder> {
    private Context lContext;
    private List<CreateBeast> beastList = new ArrayList<>();

    private final List<CreateBeast> selectedBeasts = new ArrayList<>();
    public interface OnSelectionChangedListener {
        void onSelectionChanged(int selectedCount);
    }

    private OnSelectionChangedListener selectionChangedListener;

    public void setOnSelectionChangedListener(OnSelectionChangedListener listener) {
        this.selectionChangedListener = listener;
    }

    public List<CreateBeast> getSelectedBeasts() {
        return selectedBeasts;
    }

    public BattleAdapter(Context lContext, List<CreateBeast> battleBeasts) {
        this.lContext = lContext;
        this.beastList = battleBeasts;
    }

    public void setData(List<CreateBeast> list){
        this.beastList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BattleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_battle,parent,false);
        return new BattleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BattleViewHolder holder, int position) {
        CreateBeast beast = beastList.get(position);
        holder.imageid.setImageResource(beast.getBeast().getImageResource());
        holder.hp.setText("HP:" + String.valueOf(beast.getBeast().getHeal()) + "/" +String.valueOf(beast.getBeast().getMaxHeal()));
        holder.details.setText("ATK: "+ String.valueOf(beast.getBeast().getAttack()) + " DEF: "+ String.valueOf(beast.getBeast().getDefe())+ " EXP:" + String.valueOf(beast.getBeast().getExperience()));
        holder.tick.setOnCheckedChangeListener(null);
        //Update HP
        holder.progressBarHealth.setProgress(beast.getBeast().getHeal());
        //MaxHealth
        holder.progressBarHealth.setMax(beast.getBeast().getMaxHeal());

        holder.tick.setChecked(selectedBeasts.contains(beast));

        holder.tick.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!selectedBeasts.contains(beast)) {
                    selectedBeasts.add(beast);
                }
            } else {
                selectedBeasts.remove(beast);
            }

            //call when change size
            if (selectionChangedListener != null) {
                selectionChangedListener.onSelectionChanged(selectedBeasts.size());
            }

    });
    }

    // update list in battle

    public void updateData(List<CreateBeast> newBattleList) {
        this.beastList = newBattleList;
        notifyDataSetChanged();
    }
    public void clearSelection() {
        selectedBeasts.clear();
        notifyDataSetChanged();
        if (selectionChangedListener != null) {
            selectionChangedListener.onSelectionChanged(0);
        }
    }

    public void removeFromSelected(int id) {
        Iterator<CreateBeast> iterator = selectedBeasts.iterator();
        boolean removed = false;
        while (iterator.hasNext()) {
            CreateBeast beast = iterator.next();
            if (beast.getBeast().getId() == id) {
                iterator.remove();
                removed = true;
                break;
            }
        }

        if (removed) {
            notifyDataSetChanged(); // Update List

            if (selectionChangedListener != null) {
                selectionChangedListener.onSelectionChanged(selectedBeasts.size());
            }
        }
    }


    @Override
    public int getItemCount() {
        return beastList.size();
    }

    public class BattleViewHolder  extends RecyclerView.ViewHolder{
        ImageView imageid;
        TextView hp, details;
        CheckBox tick;

        ProgressBar progressBarHealth;

        public BattleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageid = itemView.findViewById(R.id.profile_image);
            hp = itemView.findViewById(R.id.Hp);
            details = itemView.findViewById(R.id.detail);
            tick = itemView.findViewById(R.id.checkBox);
            progressBarHealth = itemView.findViewById(R.id.progressBarHP);

        }
    }
}
