package com.easyswitch.gosushi.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.adapter.FishAdapter;
import com.easyswitch.gosushi.dialog.SendDialog;
import com.easyswitch.gosushi.model.Fish;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.rvFish)
    RecyclerView rvFish;

    List<Fish> fishList = new ArrayList<>();
    FishAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        for (int i = 0; i < 24; i++) {
            fishList.add(new Fish());
        }

        adapter = new FishAdapter(this, fishList);
        rvFish.setLayoutManager(new GridLayoutManager(getApplication(), 2));
        rvFish.setAdapter(adapter);

        adapter.setOnFishClick(new FishAdapter.OnFishClick() {
            @Override
            public void onClick(View view, int position, Fish fish) {

                Intent i = new Intent(HomeActivity.this, SendDialog.class);
                startActivity(i);
            }
        });
    }
}
