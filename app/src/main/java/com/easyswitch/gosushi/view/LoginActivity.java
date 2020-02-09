package com.easyswitch.gosushi.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.rvLocation)
    RecyclerView rvLocation;

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
        ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference allRestorants = ref.child("Restourant Information");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<RestourantModel> bookModelList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    RestourantModel restourantModel = ds.getValue(RestourantModel.class);
                    restourantModels.add(restourantModel);
                }

                adapter = new LocationAdapter(getApplication(), restourantModels);
                rvLocation.setLayoutManager(new LinearLayoutManager(getApplication()));
                rvLocation.setAdapter(adapter);


                adapter.setOnLocationClick(new LocationAdapter.OnLocationClick() {
                    @Override
                    public void onClick(View view, int position, RestourantModel location) {

                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
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
}
