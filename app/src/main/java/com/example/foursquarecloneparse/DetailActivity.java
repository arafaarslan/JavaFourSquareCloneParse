package com.example.foursquarecloneparse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ImageView imageview_detail_activity;
    private TextView name_text_detail_activity,type_text_detail_activity,atmosphere_text_detail_activity;

    private String placeName;
    String latString;
    String lonString;
    Double lat;
    Double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageview_detail_activity = findViewById(R.id.imageview_detail_activity);
        name_text_detail_activity = findViewById(R.id.name_text_detail_activity);
        type_text_detail_activity = findViewById(R.id.type_text_detail_activity);
        atmosphere_text_detail_activity = findViewById(R.id.atmosphere_text_detail_activity);

        Intent intent = getIntent();
        placeName = intent.getStringExtra("name");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapDetail);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        getData();
    }

    public void getData(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Places");
        query.whereEqualTo("name",placeName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                       for(ParseObject object : objects){

                           ParseFile parseFile = (ParseFile)object.get("image");
                           parseFile.getDataInBackground(new GetDataCallback() {
                               @Override
                               public void done(byte[] data, ParseException e) {
                                   if(e == null && data != null){
                                       Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                       imageview_detail_activity.setImageBitmap(bitmap);

                                       name_text_detail_activity.setText(placeName);
                                       type_text_detail_activity.setText(object.getString("type"));
                                       atmosphere_text_detail_activity.setText(object.getString("atmpsphere"));;

                                       latString =  object.getString("latString");
                                       lonString=  object.getString("lonString");

                                       lat = Double.parseDouble(latString);
                                       lon = Double.parseDouble(lonString);

                                       mMap.clear();

                                       LatLng placeLoc = new LatLng(lat,lon);
                                       mMap.addMarker(new MarkerOptions().position(placeLoc).title(placeName));
                                       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLoc,15));
                                   }
                               }
                           });

                       }
                    }
                }else{
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}