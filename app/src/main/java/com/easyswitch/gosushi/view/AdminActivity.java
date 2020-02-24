package com.easyswitch.gosushi.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.easyswitch.gosushi.Helper.ExelConverter;
import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.adapter.AdminAdapter;
import com.easyswitch.gosushi.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class AdminActivity extends AppCompatActivity {

    @BindView(R.id.rvAdminList)
    RecyclerView rvAdminList;
    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    AdminAdapter adapter;

    private List<Product> productList;
    private DatabaseReference ref;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private ExelConverter exelConverter;
    private File exelFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);
        initComponents();
        getListFromDb();
        Timber.e(String.valueOf(productList.size()));
    }

    private void getListFromDb() {
        pbLoading.setVisibility(View.VISIBLE);
        ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference allProducts = ref.child("Product Information");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Product restourantModel = ds.getValue(Product.class);
                    productList.add(restourantModel);
                    pbLoading.setVisibility(View.INVISIBLE);
                }

                adapter = new AdminAdapter(getApplication(), productList);
                rvAdminList.setLayoutManager(new LinearLayoutManager(getApplication()));
                rvAdminList.setAdapter(adapter);

                try {
                    exelConverter.setExelFile(exelFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    exelConverter.writeExelFile(productList);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        allProducts.addListenerForSingleValueEvent(valueEventListener);
    }

    private void initComponents() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        productList = new ArrayList<>();
        exelConverter = new ExelConverter(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
