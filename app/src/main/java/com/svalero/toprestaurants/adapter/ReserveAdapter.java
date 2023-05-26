package com.svalero.toprestaurants.adapter;

import static com.svalero.toprestaurants.db.Constants.DATABASE_NAME;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.svalero.toprestaurants.CustomerDetailsActivity;
import com.svalero.toprestaurants.R;
import com.svalero.toprestaurants.ReserveDetailsActivity;
import com.svalero.toprestaurants.db.AppDatabase;
import com.svalero.toprestaurants.domain.Customer;
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
        holder.reserveId.setText(String.valueOf(reservesList.get(position).getId()));
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

        public Button deleteReserveButton;
        public View parentView;

        public ReserveHolder(View view) {
            super(view);
            parentView = view;

            reserveId = view.findViewById(R.id.reserve_id);
            reserveDate = view.findViewById(R.id.reserve_date);
            reserveDetailsButton = view.findViewById(R.id.reserve_details_button);
            deleteReserveButton = view.findViewById(R.id.delete_reserve_button);

            reserveDetailsButton.setOnClickListener(v -> seeDetails(getAdapterPosition()));
            deleteReserveButton.setOnClickListener(v -> deleteReserve(getAdapterPosition()));
        }
    }

    private void seeDetails(int position) {
        Reserve reserve = reservesList.get(position);

        Intent intent = new Intent(context, ReserveDetailsActivity.class);
        intent.putExtra("id", reserve.getId());
        context.startActivity(intent);
    }

    private void deleteReserve(int position) {
        final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Reserve reserve = reservesList.get(position);
        db.reserveDao().delete(reserve);

        reservesList.remove(position);
        notifyItemRemoved(position);
    }
}
