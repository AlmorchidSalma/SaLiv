package com.example.saliv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<VitalSignHistory> historyList;

    public HistoryAdapter(List<VitalSignHistory> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VitalSignHistory history = historyList.get(position);
        holder.dateTextView.setText(history.getDate());
        holder.heartRateTextView.setText("Fréquence cardiaque: " + history.getHeartRate() + " BPM");
        holder.spo2TextView.setText("SpO2: " + history.getSpO2() + "%");
        holder.temperatureTextView.setText("Température: " + history.getTemperature() + " °C");
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView heartRateTextView;
        TextView spo2TextView;
        TextView temperatureTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.historyDate);
            heartRateTextView = itemView.findViewById(R.id.historyHeartRate);
            spo2TextView = itemView.findViewById(R.id.historySpO2);
            temperatureTextView = itemView.findViewById(R.id.historyTemperature);
        }
    }

}
