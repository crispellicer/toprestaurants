package com.svalero.toprestaurants.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.svalero.toprestaurants.CustomerDetailsActivity;
import com.svalero.toprestaurants.R;
import com.svalero.toprestaurants.domain.Reserve;

import java.util.List;

public class ReserveAdapter extends RecyclerView.Adapter<ReserveAdapter.ReserveHolder> {

    private Context context;
    private List<Reserve> reservesList;

    public ReserveAdapter(Context context, List<Reserve> dataList) {
        this.context = context;
        this.reservesList = dataList;
    }

    @Override
    public ReserveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reserve_item, parent, false);
        return new ReserveHolder(view);
    }

    @Override
    public void onBindViewHolder(ReserveHolder holder, int position) {
        holder.reserveId.setText((int) reservesList.get(position).getId());
        holder.reserveDate.setText(reservesList.get(position).getReserveDate());
    }

    @Override
    public int getItemCount() {
        return reservesList.size();
    }

    public class ReserveHolder extends RecyclerView.ViewHolder{

        public TextView reserveId;
        public TextView reserveDate;
        public Button reserveDetailsButton;
        public View parentView;

        public ReserveHolder(View view) {
            super(view);
            parentView = view;

            reserveId = view.findViewById(R.id.reserve_id);
            reserveDate = view.findViewById(R.id.reserve_date);
            reserveDetailsButton = view.findViewById(R.id.reserve_details_button);

            reserveDetailsButton.setOnClickListener(v -> seeDetails(getAdapterPosition()));
        }
    }

    private void seeDetails(int position) {
        Intent intent = new Intent(context, CustomerDetailsActivity.class);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }
}
