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
import com.svalero.toprestaurants.domain.Customer;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerHolder> {

    private Context context;
    private List<Customer> customersList;

    public CustomerAdapter(Context context, List<Customer> dataList) {
        this.context = context;
        this.customersList = dataList;
    }

    @Override
    public CustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_item, parent, false);
        return new CustomerHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerHolder holder, int position) {
        holder.customerName.setText(customersList.get(position).getName());
        holder.customerSurname.setText(customersList.get(position).getSurname());
    }

    @Override
    public int getItemCount() {
        return customersList.size();
    }

    public class CustomerHolder extends RecyclerView.ViewHolder{

        public TextView customerName;
        public TextView customerSurname;
        public Button customerDetailsButton;
        public View parentView;

        public CustomerHolder(View view) {
            super(view);
            parentView = view;

            customerName = view.findViewById(R.id.customer_name);
            customerSurname = view.findViewById(R.id.customer_surname);
            customerDetailsButton = view.findViewById(R.id.customer_details_button);

            customerDetailsButton.setOnClickListener(v -> seeDetails(getAdapterPosition()));
        }
    }

    private void seeDetails(int position) {
        Intent intent = new Intent(context, CustomerDetailsActivity.class);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }
}
