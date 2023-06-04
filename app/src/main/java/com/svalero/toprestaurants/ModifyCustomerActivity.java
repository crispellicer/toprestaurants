package com.svalero.toprestaurants;

import static com.svalero.toprestaurants.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.toprestaurants.db.AppDatabase;
import com.svalero.toprestaurants.domain.Customer;

public class ModifyCustomerActivity extends AppCompatActivity {

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_customer);

        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Customer customer = db.customerDao().getById(id);
        fillData(customer);
    }

    public void modifyCustomerButton (View view) {
        EditText etName = findViewById(R.id.edit_text_modify_customer_name);
        EditText etSurname = findViewById(R.id.edit_text_modify_customer_surname);
        EditText etTelephone = findViewById(R.id.edit_text_modify_customer_telephone);
        EditText etBirthDate = findViewById(R.id.edit_text_modify_customer_birthdate);
        CheckBox checkVip = findViewById(R.id.check_box_modify_vip);

        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String telephone = etTelephone.getText().toString();
        String birthDate = etBirthDate.getText().toString();
        boolean vip = checkVip.isChecked();

        Customer customer = new Customer(id, name, surname, telephone, birthDate, vip);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.sure)
                    .setTitle(R.string.modify_customer)
                    .setPositiveButton(R.string.yes, (dialog, id) -> {

                        db.customerDao().update(customer);

                        Intent intent = new Intent(this, CustomersListView.class);
                        intent.putExtra("id", customer.getId());
                        this.startActivity(intent);
                    })
                    .setNegativeButton(R.string.no, (dialog, id) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (SQLiteConstraintException sce) {
            Snackbar.make(etName, R.string.error_message, BaseTransientBottomBar.LENGTH_LONG);
        }
    }

    private void fillData(Customer customer) {
        EditText etName = findViewById(R.id.edit_text_modify_customer_name);
        EditText etSurname = findViewById(R.id.edit_text_modify_customer_surname);
        EditText etTelephone = findViewById(R.id.edit_text_modify_customer_telephone);
        EditText etBirthDate = findViewById(R.id.edit_text_modify_customer_birthdate);
        CheckBox checkVip = findViewById(R.id.check_box_modify_vip);

        etName.setText(customer.getName());
        etSurname.setText(customer.getSurname());
        etTelephone.setText(customer.getTelephone());
        etBirthDate.setText(customer.getBirthDate());
        checkVip.setChecked(customer.isVip());
    }

    public void cancelModifyButton(View view) {
        onBackPressed();
    }
}