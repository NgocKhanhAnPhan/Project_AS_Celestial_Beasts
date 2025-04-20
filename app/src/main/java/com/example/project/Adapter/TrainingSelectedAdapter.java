package com.example.project.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.BeastStorage;
import com.example.project.Model.TrainingSelected;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class TrainingSelectedAdapter extends RecyclerView.Adapter<TrainingSelectedAdapter.TrainingSelectedViewHolder> {

    private Context tContext;
    private static List<TrainingSelected> selectedList = new ArrayList<>();
    private List<TrainingSelected> selectedItems = new ArrayList<>();

    private int selectedPosition = RecyclerView.NO_POSITION;
    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    private OnItemSelectedListener listener;
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public static List<TrainingSelected> getSelectedList() {
        return selectedList;
    }


    public void setData(List<TrainingSelected> selectedList) {
        TrainingSelectedAdapter.selectedList = selectedList;
        notifyDataSetChanged();
    }
    public TrainingSelectedAdapter(Context tContext){
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public TrainingSelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(tContext).inflate(R.layout.detail_training, parent, false);
        return new TrainingSelectedAdapter.TrainingSelectedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingSelectedViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TrainingSelected traning = selectedList.get(position);
        if (traning == null) return;

        holder.tvName.setText(traning.getName());
        holder.tvDecip.setText(traning.getDescrip());
        holder.btn_tick.setChecked(position == selectedPosition);

        holder.btn_tick.setOnClickListener(v -> {
            if (selectedPosition == position) {
                // Continue click to do not choose
                selectedPosition = RecyclerView.NO_POSITION;
                selectedItems.clear();
            } else {
                // Choose another item
                selectedPosition = position;
                selectedItems.clear();
                selectedItems.add(traning);
            }
            if (listener != null) {
                listener.onItemSelected(position);
            }

            notifyDataSetChanged();
        });


    }

    @Override
    public int getItemCount() {
        return selectedList.size();
    }

    public class TrainingSelectedViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvDecip;
        private RadioButton btn_tick;
        @SuppressLint("WrongViewCast")
        public TrainingSelectedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textView32);
            tvDecip = itemView.findViewById(R.id.textView33);
            btn_tick = itemView.findViewById(R.id.btn_mode);
        }
    }

    public List<TrainingSelected> getSelectedItems() {
        return selectedItems;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}


