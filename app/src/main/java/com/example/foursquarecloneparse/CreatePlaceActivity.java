package com.example.foursquarecloneparse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

public class CreatePlaceActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image_view_create_place_activity;
    private EditText name_create_place_activity, type_create_place_activity, atmosphere_text_create_place_activity;
    private Button next_btn_create_place_activity;
    private Bitmap chosenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_place);

        image_view_create_place_activity = findViewById(R.id.image_view_create_place_activity);
        name_create_place_activity = findViewById(R.id.name_create_place_activity);
        type_create_place_activity = findViewById(R.id.type_create_place_activity);
        atmosphere_text_create_place_activity = findViewById(R.id.atmosphere_text_create_place_activity);
        next_btn_create_place_activity = findViewById(R.id.next_btn_create_place_activity);

        next_btn_create_place_activity.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next_btn_create_place_activity) {
            nextBtnCreatePlaceActivityPressed();
        }
    }

    private void nextBtnCreatePlaceActivityPressed() {
        Places places = Places.getInstance();
        places.setName(name_create_place_activity.getText().toString());
        places.setType(type_create_place_activity.getText().toString());
        places.setAtmpsphere(atmosphere_text_create_place_activity.getText().toString());
        places.setImage(chosenImage);
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
    }

    public void selectPicture(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                chosenImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                image_view_create_place_activity.setImageBitmap(chosenImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}