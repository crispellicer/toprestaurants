package com.svalero.toprestaurants;

import static com.svalero.toprestaurants.db.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.svalero.toprestaurants.adapter.CustomerAdapter;
import com.svalero.toprestaurants.adapter.ReserveAdapter;
import com.svalero.toprestaurants.db.AppDatabase;
import com.svalero.toprestaurants.domain.Customer;
import com.svalero.toprestaurants.domain.Reserve;

import java.util.ArrayList;
import java.util.List;

public class ReservesListView extends AppCompatActivity {

    private List<Reserve> reservesList;
    private ReserveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves_list_view);

        reservesList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.reserves_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReserveAdapter(this, reservesList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        reservesList.clear();
        reservesList.addAll(db.reserveDao().getAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_reserv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.register_reserve) {
            Intent intent = new Intent(this, RegisterReserveActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

    public void goBackButton(View view) {
        onBackPressed();
    }
}