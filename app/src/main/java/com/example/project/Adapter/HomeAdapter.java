package com.example.project.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Beast;
import com.example.project.Model.CreateBeast;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private Context hContext;
    private List<CreateBeast> beastList = new ArrayList<>();

    private List<CreateBeast> selectedBeasts = new ArrayList<>();


    public List<CreateBeast> getSelectedBeasts() {
        return selectedBeasts;  // Return the list of selected beasts
    }


    public HomeAdapter(Context hContext) {
        this.hContext = hContext;
    }


    public void setData(List<CreateBeast> list) {
        this.beastList = list;
        notifyDataSetChanged();
    }

    // Update RecyclerView when selected
    public void setSelectedBeast(CreateBeast beast) {
        if (selectedBeasts.contains(beast)) {
            selectedBeasts.remove(beast);
        } else {
            selectedBeasts.add(beast);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(hContext).inflate(R.layout.home_detail_beast, parent, false);
        return new HomeViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
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
        holder.imagebeast.setImageResource(beast.getBeast().getImageResource());

        holder.btn_tick.setOnCheckedChangeListener(null); // Reset listener when view is reuse

        // Set the checkbox checked based on whether the beast is in selectedBeasts
        holder.btn_tick.setChecked(selectedBeasts.contains(beast));

        holder.btn_tick.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!selectedBeasts.contains(beast)) {
                    selectedBeasts.add(beast);  // Add to selected list
                }
            } else {
                selectedBeasts.remove(beast);  // Remove from selected list
            }
        });

    }

    @Override
    public int getItemCount() {
        return (beastList != null) ? beastList.size() : 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder{

        private ImageView imagebeast;

        private TextView tvele, tvcha, tvdefe, tvatt, tvheal, tvmaxheal, tvname, tvex;

        CheckBox btn_tick;


        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imagebeast = itemView.findViewById(R.id.imageView2);
            tvele = itemView.findViewById(R.id.c_elem);
            tvcha = itemView.findViewById(R.id.c_char);
            tvatt =itemView.findViewById(R.id.c_attack);
            tvdefe= itemView.findViewById(R.id.c_defen);
            tvheal = itemView.findViewById(R.id.c_health);
            tvmaxheal = itemView.findViewById(R.id.c_maxhel);
            btn_tick = itemView.findViewById(R.id.checkBox2);
            tvname = itemView.findViewById(R.id.textView25);
            tvex = itemView.findViewById(R.id.textView27);
        }

    }
}
