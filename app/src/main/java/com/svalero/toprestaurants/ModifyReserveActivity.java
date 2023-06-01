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
import com.svalero.toprestaurants.domain.Reserve;

public class ModifyReserveActivity extends AppCompatActivity {

    private long id;
    private long customerId;
    private long restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_reserve);

        Intent intent = new Intent(getIntent());
        id = getIntent().getLongExtra("id",0);


        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        Reserve reserve = db.reserveDao().getById(id);
        customerId = reserve.getCustomerId();
        restaurantId = reserve.getRestaurantId();
        fillData(reserve);
    }

    public void modifyReserveButton(View view) {
        EditText etPeople = findViewById(R.id.edit_text_modify_reserve_people);
        EditText etTables = findViewById(R.id.edit_text_modify_reserve_tables);
        EditText etReserveDate = findViewById(R.id.edit_text_modify_reserve_date);
        CheckBox checkPaid = findViewById(R.id.check_box_modify_paid);
        CheckBox checkAllergic = findViewById(R.id.check_box_modify_allergic);

        String peopleString = etPeople.getText().toString();
        int people = Integer.parseInt(peopleString);
        String tablesString = etTables.getText().toString();
        int tables = Integer.parseInt(tablesString);
        String reserveDate = etReserveDate.getText().toString();
        boolean isPaid = checkPaid.isChecked();
        boolean allergic = checkAllergic.isChecked();

        Reserve reserve = new Reserve(id, customerId, restaurantId, people, tables, reserveDate, isPaid, allergic);

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.sure)
                    .setTitle(R.string.modify_reserve)
                    .setPositiveButton(R.string.yes, (dialog, id) -> {

                        db.reserveDao().update(reserve);

                        Intent intent = new Intent(this, ReservesListView.class);
                        intent.putExtra("id", reserve.getId());
                        this.startActivity(intent);
                    })
                    .setNegativeButton(R.string.no, (dialog, is) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (SQLiteConstraintException sce) {
            Snackbar.make(etPeople, R.string.error_message, BaseTransientBottomBar.LENGTH_LONG);
        }
    }

    private void fillData(Reserve reserve) {
        EditText etPeople = findViewById(R.id.edit_text_modify_reserve_people);
        EditText etTables = findViewById(R.id.edit_text_modify_reserve_tables);
        EditText etReserveDate = findViewById(R.id.edit_text_modify_reserve_date);
        CheckBox checkPaid = findViewById(R.id.check_box_modify_paid);
        CheckBox checkAllergic = findViewById(R.id.check_box_modify_allergic);

        etPeople.setText(String.valueOf(reserve.getPeople()));
        etTables.setText(String.valueOf(reserve.getTables()));
        etReserveDate.setText(reserve.getReserveDate());
        checkPaid.setChecked(reserve.isPaid());
        checkAllergic.setChecked(reserve.isAllergic());
    }

    public void cancelModifyButton(View view) {
        onBackPressed();
    }
}