package com.svalero.toprestaurants;

import static com.svalero.toprestaurants.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.svalero.toprestaurants.db.AppDatabase;
import com.svalero.toprestaurants.domain.Customer;

public class RegisterCustomerActivity extends AppCompatActivity {

    private int SELECT_PICTURE_RESULT = 1;
    private int REQUEST_IMAGE_CAPTURE = 2;

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

    public void selectPicture(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Select an option")
                .setPositiveButton("Take a photo",
                        (dialog, which) -> {
                            int result = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
                            if (result != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                            }


                            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
                            }
                        })
                .setNegativeButton("Choose a picture from gallery",
                        (dialog, which) -> {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, SELECT_PICTURE_RESULT);
                        }
                );
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == SELECT_PICTURE_RESULT) && (resultCode == RESULT_OK)
                && (data != null)) {
            Picasso.get().load(data.getData()).noPlaceholder().centerCrop().fit()
                    .into((ImageView) findViewById(R.id.customer_image));
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = findViewById(R.id.customer_image);
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public void goBackButton(View view) {
        onBackPressed();
    }
}