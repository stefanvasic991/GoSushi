package com.easyswitch.gosushi.dialog;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.model.Product;
import com.easyswitch.gosushi.view.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jpos.POSPrinterConst;

public class SendDialog extends AppCompatActivity {

    @SuppressLint("SimpleDateFormat")
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");

    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvProduct)
    TextView tvProduct;
    @BindView(R.id.tvDateTime)
    TextView tvDateTime;
    @BindView(R.id.tvDateExpired)
    TextView tvDateExpired;
    @BindView(R.id.tvLot)
    EditText etLot;
    @BindView(R.id.etWeight)
    EditText etWeight;
    RelativeLayout llTicket;
//    @BindView(R.id.llTicket)

    private String location, productName;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser firebaseUser;
    private DatabaseReference ref;
    Date printTime, expiredTime;
    Bitmap bmp;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dialog);
        ButterKnife.bind(this);

        llTicket = findViewById(R.id.llTicket);

        initComponents();

        location = getIntent().getStringExtra("location");
        productName = getIntent().getStringExtra("product");
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent));
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL);

        printTime = new Date(System.currentTimeMillis());
        expiredTime = new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(8));

        tvLocation.setText(location);
        tvProduct.setText(productName);
        tvDateTime.setText(dateFormat.format(printTime));
        tvDateExpired.setText(dateFormat.format(expiredTime));
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    @OnClick(R.id.tvCancel)
    public void cancel() {
        finish();
    }

    @OnClick(R.id.tvSend)
    public void send() {
        if (etWeight.getText().toString().trim().isEmpty()) {
            etWeight.setError("Unesi težinu");
            return;
        }
        Product product = new Product();
        product.setStartDate(tvDateTime.getText().toString());
        product.setEndDate(tvDateExpired.getText().toString());
        product.setMass(Integer.parseInt(etWeight.getText().toString()));
        product.setName(tvProduct.getText().toString());
        product.setLocationName(location);
        pushInformationToFireBase(product);

        bmp = loadBitmapFromView(llTicket, llTicket.getWidth(), llTicket.getHeight());
        HomeActivity.getPrinterInstance().printImage(bmp, llTicket.getWidth(), POSPrinterConst.PTR_BC_LEFT, 0, 2);
        finish();
    }

    private void initComponents() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
    }

    private void pushInformationToFireBase(Product product) {
        ref.child("Product Information").push().setValue(product);
    }
}