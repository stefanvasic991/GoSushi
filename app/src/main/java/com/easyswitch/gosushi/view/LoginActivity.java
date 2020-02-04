package com.easyswitch.gosushi.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.adapter.LocationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.rvLocation)
    RecyclerView rvLocation;

    LocationAdapter adapter;
    List<String> locationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        locationList.add("Banovo Brdo");
        locationList.add("Vračar");
        locationList.add("Vračar Crveni Krst");
        locationList.add("Belville");

        adapter = new LocationAdapter(this, locationList);
        rvLocation.setLayoutManager(new LinearLayoutManager(this));
        rvLocation.setAdapter(adapter);

        adapter.setOnLocationClick(new LocationAdapter.OnLocationClick() {
            @Override
            public void onClick(View view, int position, String location) {
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }

    @OnClick(R.id.fab)
    public void register() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}
