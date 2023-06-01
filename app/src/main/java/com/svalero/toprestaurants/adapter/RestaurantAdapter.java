package com.svalero.toprestaurants.adapter;

import static com.svalero.toprestaurants.db.Constants.DATABASE_NAME;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.svalero.toprestaurants.ModifyRestaurantActivity;
import com.svalero.toprestaurants.R;
import com.svalero.toprestaurants.RestaurantDetailsActivity;
import com.svalero.toprestaurants.db.AppDatabase;
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
        public Button modifyRestaurantButton;
        public Button deleteRestaurantButton;
        public View parentView;

        public RestaurantHolder(View view) {
            super(view);
            parentView = view;

            restaurantName = view.findViewById(R.id.restaurant_name);
            restaurantType = view.findViewById(R.id.restaurant_type);
            restaurantDetailsButton = view.findViewById(R.id.restaurant_details_button);
            modifyRestaurantButton = view.findViewById(R.id.modify_restaurant_button);
            deleteRestaurantButton = view.findViewById(R.id.delete_restaurant_button);

            restaurantDetailsButton.setOnClickListener(v -> seeDetails(getAdapterPosition()));
            modifyRestaurantButton.setOnClickListener(v -> modifyRestaurant(getAdapterPosition()));
            deleteRestaurantButton.setOnClickListener(v -> deleteRestaurant(getAdapterPosition()));
        }
    }

    private void seeDetails(int position) {
        Restaurant restaurant = restaurantsList.get(position);

        Intent intent = new Intent(context, RestaurantDetailsActivity.class);
        intent.putExtra("name", restaurant.getName());
        context.startActivity(intent);
    }

    private void modifyRestaurant(int position){
        Restaurant restaurant = restaurantsList.get(position);

        Intent intent = new Intent(context, ModifyRestaurantActivity.class);
        intent.putExtra("id", restaurant.getId());
        context.startActivity(intent);
    }

    private void deleteRestaurant(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.sure)
                .setTitle(R.string.delete_restaurant)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries().build();
                    Restaurant restaurant = restaurantsList.get(position);
                    db.restaurantDao().delete(restaurant);

                    restaurantsList.remove(position);
                    notifyItemRemoved(position);
                })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
