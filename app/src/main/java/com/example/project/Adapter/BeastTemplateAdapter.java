package com.example.project.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Beast;
import com.example.project.R;

import java.util.List;

public class BeastTemplateAdapter extends RecyclerView.Adapter<BeastTemplateAdapter.BeastViewHolder> {

    private Context mContext;
    private static List<Beast> mListBeast;

    private static int selectedPosition = -1;

    public static Beast getSelectedBeast() {
        if (selectedPosition != -1 && mListBeast != null && selectedPosition < mListBeast.size()) {
            return mListBeast.get(selectedPosition);
        }
        return null;
    }



    public BeastTemplateAdapter(Context mContext){
        this.mContext = mContext;
    }

    public static void clearSelection() {
        selectedPosition = -1;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Beast> list){
        this.mListBeast = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BeastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_beast, parent, false);
        return new BeastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeastViewHolder holder, int position) {
        Beast beast = mListBeast.get(position);

        holder.imagebeast.setImageResource(beast.getImageResource());
        holder.tvele.setText(beast.getEle());
        holder.tvcha.setText(beast.getChara());
        holder.tvdefe.setText(String.valueOf(beast.getDefe()));
        holder.tvatt.setText(String.valueOf(beast.getAttack()));
        holder.tvheal.setText(String.valueOf(beast.getHeal()));
        holder.tvmaxheal.setText(String.valueOf(beast.getMaxHeal()));
        // Set radio state
        holder.radioButton.setChecked(position == selectedPosition);

        // Click listener for radio
        holder.radioButton.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
        });

        // Optional: Also select on card click
        holder.itemView.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mListBeast.size();
    }

    public class BeastViewHolder extends RecyclerView.ViewHolder{

        private ImageView imagebeast;

        private TextView tvele, tvcha, tvdefe, tvatt, tvheal, tvmaxheal;

        RadioButton radioButton;


        public BeastViewHolder(@NonNull View itemView) {
            super(itemView);
            imagebeast = itemView.findViewById(R.id.imageView2);
            tvele = itemView.findViewById(R.id.c_elem);
            tvcha = itemView.findViewById(R.id.c_char);
            tvatt =itemView.findViewById(R.id.c_attack);
            tvdefe= itemView.findViewById(R.id.c_defen);
            tvheal = itemView.findViewById(R.id.c_health);
            tvmaxheal = itemView.findViewById(R.id.c_maxhel);
            radioButton = itemView.findViewById(R.id.radioButton);
        }

    }
}
