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

import com.svalero.toprestaurants.CustomerDetailsActivity;
import com.svalero.toprestaurants.ModifyCustomerActivity;
import com.svalero.toprestaurants.R;
import com.svalero.toprestaurants.db.AppDatabase;
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
        public Button modifyCustomerButton;
        public Button deleteCustomerButton;
        public View parentView;

        public CustomerHolder(View view) {
            super(view);
            parentView = view;

            customerName = view.findViewById(R.id.customer_name);
            customerSurname = view.findViewById(R.id.customer_surname);
            customerDetailsButton = view.findViewById(R.id.customer_details_button);
            modifyCustomerButton = view.findViewById(R.id.modify_customer_button);
            deleteCustomerButton = view.findViewById(R.id.delete_customer_button);

            customerDetailsButton.setOnClickListener(v -> seeDetails(getAdapterPosition()));
            modifyCustomerButton.setOnClickListener(v -> modifyCustomer(getAdapterPosition()));
            deleteCustomerButton.setOnClickListener(v -> deleteCustomer(getAdapterPosition()));
        }
    }

    private void seeDetails(int position) {
        Customer customer = customersList.get(position);

        Intent intent = new Intent(context, CustomerDetailsActivity.class);
        intent.putExtra("name", customer.getName());
        context.startActivity(intent);
    }

    private void modifyCustomer(int position){
        Customer customer = customersList.get(position);

        Intent intent = new Intent(context, ModifyCustomerActivity.class);
        intent.putExtra("id", customer.getId());
        context.startActivity(intent);
    }

    private void deleteCustomer(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure?")
                .setTitle("Delete customer")
                .setPositiveButton("Yes", (dialog, id) -> {
                    final AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries().build();
                    Customer customer = customersList.get(position);
                    db.customerDao().delete(customer);

                    customersList.remove(position);
                    notifyItemRemoved(position);
                })
                .setNegativeButton("No", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
