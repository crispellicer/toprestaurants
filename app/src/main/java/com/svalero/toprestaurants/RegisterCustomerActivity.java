package com.svalero.toprestaurants;

import static com.svalero.toprestaurants.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.svalero.toprestaurants.db.AppDatabase;
import com.svalero.toprestaurants.domain.Customer;

public class RegisterCustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);
    }

    public void saveButton(View view) {
        EditText etName = findViewById(R.id.edit_text_customer_name);
        EditText etSurname = findViewById(R.id.edit_text_customer_surname);
        EditText etTelephone = findViewById(R.id.edit_text_customer_telephone);
        EditText etBirthDate = findViewById(R.id.edit_text_customer_birthdate);
        CheckBox checkVip = findViewById(R.id.check_box_vip);

        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String telephone = etTelephone.getText().toString();
        String birthDate = etBirthDate.getText().toString();
        boolean vip = checkVip.isChecked();

        Customer customer = new Customer(name, surname, telephone, birthDate, vip);
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        db.customerDao().insert(customer);

        Toast.makeText(this, "Customer has been registered", Toast.LENGTH_LONG).show();
        etName.setText("");
        etSurname.setText("");
        etTelephone.setText("");
        etBirthDate.setText("");
        checkVip.setChecked(false);
        etName.requestFocus();
    }

    public void goBackButton(View view) {
        onBackPressed();
    }
}