package com.easyswitch.gosushi.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.adapter.ProductAdapter;
import com.easyswitch.gosushi.dialog.SendDialog;
import com.easyswitch.gosushi.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.rvFish)
    RecyclerView rvFish;

    List<Product> productList = new ArrayList<>();
    ProductAdapter adapter;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        location = getIntent().getStringExtra("location");

        productList.add(new Product("Tuna"));
        productList.add(new Product("Brancin"));
        productList.add(new Product("Orada"));
        productList.add(new Product("Losos"));
        productList.add(new Product("Piletina"));
        productList.add(new Product("Teletina"));
        productList.add(new Product("Pirinač"));
        productList.add(new Product("Šargarepa"));
        productList.add(new Product("Goma"));
        productList.add(new Product("Mango"));
        productList.add(new Product("Avokado"));


        adapter = new ProductAdapter(this, productList);
        rvFish.setLayoutManager(new GridLayoutManager(getApplication(), 2));
        rvFish.setAdapter(adapter);

        adapter.setOnFishClick(new ProductAdapter.OnFishClick() {
            @Override
            public void onClick(View view, int position, Product product) {

                Intent i = new Intent(HomeActivity.this, SendDialog.class);
                i.putExtra("location", location);
                i.putExtra("product", product.getName());
                startActivity(i);
            }
        });
    }
}
