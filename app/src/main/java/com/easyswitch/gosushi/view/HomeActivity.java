package com.easyswitch.gosushi.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.adapter.FishAdapter;
import com.easyswitch.gosushi.model.Fish;

import java.util.ArrayList;
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

        for (int i = 0; i < 7l; i++) {
            fishList.add(new Fish());
        }

        adapter = new FishAdapter(this, fishList);
        rvFish.setLayoutManager(new LinearLayoutManager(getApplication()));
        rvFish.setAdapter(adapter);

        adapter.setOnFishClick(new FishAdapter.OnFishClick() {
            @Override
            public void onClick(View view, int position, Fish fish) {
                Toast.makeText(HomeActivity.this, "bluetooth printer", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
