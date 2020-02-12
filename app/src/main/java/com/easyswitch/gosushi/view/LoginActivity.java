package com.easyswitch.gosushi.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.adapter.LocationAdapter;
import com.easyswitch.gosushi.model.RestourantModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.rvLocation)
    RecyclerView rvLocation;
    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    LocationAdapter adapter;

    private List<RestourantModel> restourantModels;
    private DatabaseReference ref;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initComponents();
        getListFromDb();

    }

    @OnClick(R.id.fab)
    public void register() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
        finish();
    }

    private void getListFromDb() {
        pbLoading.setVisibility(View.VISIBLE);
        ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference allRestorants = ref.child("Restourant Information");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    RestourantModel restourantModel = ds.getValue(RestourantModel.class);
                    restourantModels.add(restourantModel);
                    pbLoading.setVisibility(View.INVISIBLE);
                }

                adapter = new LocationAdapter(getApplication(), restourantModels);
                rvLocation.setLayoutManager(new LinearLayoutManager(getApplication()));
                rvLocation.setAdapter(adapter);

                adapter.setOnLocationClick(new LocationAdapter.OnLocationClick() {
                    @Override
                    public void onClick(View view, int position, RestourantModel location) {

                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        i.putExtra("location", location.getLocation());
                        startActivity(i);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        allRestorants.addListenerForSingleValueEvent(valueEventListener);
    }

    private void initComponents() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        restourantModels = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id  == R.id.list_item) {
            Intent i = new Intent(this, AdminActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
