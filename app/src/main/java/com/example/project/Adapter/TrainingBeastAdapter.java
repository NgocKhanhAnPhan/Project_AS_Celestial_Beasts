package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.CreateBeast;
import com.example.project.R;
import com.example.project.View.Training;

import java.util.ArrayList;
import java.util.List;

public class TrainingBeastAdapter extends RecyclerView.Adapter<TrainingBeastAdapter.TrainingBeastViewHolder> {

    private Context lContext;
    private List<CreateBeast> beastList = new ArrayList<>();

    private int selectedPosition = -1; // No item selected by default


    public TrainingBeastAdapter(Context lContext, List<CreateBeast> beastList) {
        this.lContext = lContext;
        this.beastList = beastList;
    }

    @NonNull
    @Override
    public TrainingBeastAdapter.TrainingBeastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(lContext).inflate(R.layout.home_detail_beast, parent, false);
        return new TrainingBeastAdapter.TrainingBeastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingBeastAdapter.TrainingBeastViewHolder holder, int position) {
        CreateBeast beast = beastList.get(position);
        if (beast == null) return;

        holder.tvname.setText(beast.getCustomName());
        holder.tvele.setText( beast.getBeast().getEle());
        holder.tvcha.setText(beast.getBeast().getChara());
        holder.tvheal.setText(String.valueOf(beast.getBeast().getHeal()));
        holder.tvatt.setText(String.valueOf(beast.getBeast().getAttack()));
        holder.tvdefe.setText(String.valueOf(beast.getBeast().getDefe()));
        holder.tvex.setText(String.valueOf(beast.getBeast().getExperience()));
        holder.tvmaxheal.setText(String.valueOf(beast.getBeast().getMaxHeal()));
        holder.imageBeast.setImageResource(beast.getBeast().getImageResource());

        holder.radioButton.setChecked(position == selectedPosition);

        holder.radioButton.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged(); // Refresh to update selected radio button
        });


    }

    @Override
    public int getItemCount() {
        return (beastList != null) ? beastList.size() : 0;
    }

    public void updateData(List<CreateBeast> newList) {
        beastList.clear();
        beastList.addAll(newList);
        notifyDataSetChanged();
    }


    public class TrainingBeastViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageBeast;
        private TextView tvele, tvcha, tvdefe, tvatt, tvheal, tvmaxheal, tvname, tvex;
        private RadioButton radioButton;

        public TrainingBeastViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBeast = itemView.findViewById(R.id.imageView2);
            tvele = itemView.findViewById(R.id.c_elem);
            tvcha = itemView.findViewById(R.id.c_char);
            tvatt = itemView.findViewById(R.id.c_attack);
            tvdefe = itemView.findViewById(R.id.c_defen);
            tvheal = itemView.findViewById(R.id.c_health);
            tvmaxheal = itemView.findViewById(R.id.c_maxhel);
            tvname = itemView.findViewById(R.id.textView25);
            tvex = itemView.findViewById(R.id.textView27);
            radioButton = itemView.findViewById(R.id.btn_tick);
        }
    }

    public CreateBeast getSelectedBeast() {
        if (selectedPosition != -1 && selectedPosition < beastList.size()) {
            return beastList.get(selectedPosition);
        }
        return null;
    }

    public int getSelectedBeastId() {
        if (selectedPosition != -1 && selectedPosition < beastList.size()) {
            return beastList.get(selectedPosition).getBeast().getId();
        }
        return -1;
    }


}
