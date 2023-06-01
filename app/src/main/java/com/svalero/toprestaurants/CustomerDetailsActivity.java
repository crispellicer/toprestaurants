package com.svalero.toprestaurants;

import static com.svalero.toprestaurants.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.svalero.toprestaurants.db.AppDatabase;
import com.svalero.toprestaurants.domain.Customer;

public class CustomerDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if (name == null)
            return;

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Customer customer = db.customerDao().getByName(name);
        Toast.makeText(this, getString(R.string.customer) + customer.getName(), Toast.LENGTH_LONG).show();

        fillData(customer);
    }

    private void fillData(Customer customer) {
        TextView tvName = findViewById(R.id.tv_customer_name_details);
        TextView tvSurname = findViewById(R.id.tv_customer_surname_details);
        TextView tvTelephone = findViewById(R.id.tv_customer_telephone_details);
        TextView tvBirthDate = findViewById(R.id.tv_customer_birthdate_details);
        CheckBox checkVip = findViewById(R.id.cb_customer_vip_details);

        tvName.setText(customer.getName());
        tvSurname.setText(customer.getSurname());
        tvTelephone.setText(customer.getTelephone());
        tvBirthDate.setText(customer.getBirthDate());
        checkVip.setChecked(customer.isVip());
    }

    public void goBackButton(View view) {
        onBackPressed();
    }
}
