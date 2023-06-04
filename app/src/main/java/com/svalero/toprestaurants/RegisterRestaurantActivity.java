package com.svalero.toprestaurants;

import static com.svalero.toprestaurants.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.database.sqlite.SQLiteConstraintException;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.svalero.toprestaurants.db.AppDatabase;
import com.svalero.toprestaurants.domain.Restaurant;

public class RegisterRestaurantActivity extends AppCompatActivity {

    private MapView restaurantMap;
    private Point point;
    private PointAnnotationManager pointAnnotationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);

        restaurantMap = findViewById(R.id.restaurantMap);
        GesturesPlugin gesturesPlugin = GesturesUtils.getGestures(restaurantMap);
        gesturesPlugin.addOnMapClickListener(point -> {
            removeAllMarkers();
            this.point = point;
            addMarker(point);
            return true;
        });

        initializePointManager();
    }

    public void saveButton(View view) {
        EditText etName = findViewById(R.id.edit_text_restaurant_name);
        EditText etTimetable = findViewById(R.id.edit_text_restaurant_timetable);
        EditText etType = findViewById(R.id.edit_text_restaurant_type);
        EditText etReservePrice = findViewById(R.id.edit_text_restaurant_reserveprice);
        CheckBox checkVeganMenu = findViewById(R.id.check_box_veganmenu);
        EditText etWebsite = findViewById(R.id.edit_text_restaurant_website);

        String name = etName.getText().toString();
        String timetable = etTimetable.getText().toString();
        String type = etType.getText().toString();
        String reservePriceString = etReservePrice.getText().toString();
        double reservePrice = Double.parseDouble(reservePriceString);
        boolean veganMenu = checkVeganMenu.isChecked();
        String website = etWebsite.getText().toString();

        if (point == null) {
            Toast.makeText(this, R.string.location_selection, Toast.LENGTH_LONG).show();
            return;
        }

        Restaurant restaurant = new Restaurant(name, timetable, type, reservePrice,veganMenu, website, point.longitude(), point.latitude());
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
        try {
            db.restaurantDao().insert(restaurant);

            Toast.makeText(this, R.string.restaurant_register, Toast.LENGTH_LONG).show();
            etName.setText("");
            etTimetable.setText("");
            etType.setText("");
            etReservePrice.setText("");
            checkVeganMenu.setChecked(false);
            etWebsite.setText("");
            etName.requestFocus();
        } catch (SQLiteConstraintException sce) {
            Toast.makeText(this, R.string.error_message, Toast.LENGTH_LONG).show();
            //Snackbar.make(etName, "An error has occurred.", BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }
    private void initializePointManager() {
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(restaurantMap);
        AnnotationConfig annotationConfig = new AnnotationConfig();
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, annotationConfig);
    }

    public void goBackButton(View view) {
        onBackPressed();
    }

    private void addMarker(Point point) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(BitmapFactory.decodeResource(getResources(), R.mipmap.red_marker));
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    private void removeAllMarkers() {
        pointAnnotationManager.deleteAll();
    }
}