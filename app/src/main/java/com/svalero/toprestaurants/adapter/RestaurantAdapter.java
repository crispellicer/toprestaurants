package com.svalero.toprestaurants.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.svalero.toprestaurants.R;
import com.svalero.toprestaurants.RestaurantDetailsActivity;
import com.svalero.toprestaurants.domain.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder> {

    private Context context;
    private List<Restaurant> restaurantsList;

    public RestaurantAdapter(Context context, List<Restaurant> dataList) {
        this.context = context;
        this.restaurantsList = dataList;
    }

    @Override
    public RestaurantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantHolder holder, int position) {
        holder.restaurantName.setText(restaurantsList.get(position).getName());
        holder.restaurantType.setText(restaurantsList.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }

    public class RestaurantHolder extends RecyclerView.ViewHolder{

        public TextView restaurantName;
        public TextView restaurantType;
        public Button restaurantDetailsButton;
        public View parentView;

        public RestaurantHolder(View view) {
            super(view);
            parentView = view;

            restaurantName = view.findViewById(R.id.customer_name);
            restaurantType = view.findViewById(R.id.customer_surname);
            restaurantDetailsButton = view.findViewById(R.id.customer_details_button);

            restaurantDetailsButton.setOnClickListener(v -> seeDetails(getAdapterPosition()));
        }
    }

    private void seeDetails(int position) {
        Intent intent = new Intent(context, RestaurantDetailsActivity.class);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }
}
