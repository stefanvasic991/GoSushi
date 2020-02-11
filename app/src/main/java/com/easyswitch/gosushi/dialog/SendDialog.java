package com.easyswitch.gosushi.dialog;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.model.Fish;
import com.easyswitch.gosushi.model.RestourantModel;
import com.easyswitch.gosushi.view.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendDialog extends AppCompatActivity {

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
    private final String kg = "kg";

    @BindView(R.id.tvDateTime)
    TextView tvDateTime;
    @BindView(R.id.tvDateExpired)
    TextView tvDateExpired;
    @BindView(R.id.etWeight)
    EditText etWeight;
    private String location;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser firebaseUser;
    private DatabaseReference ref;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dialog);
        ButterKnife.bind(this);
        initComponents();
        location = getIntent().getStringExtra("location");
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent));
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL);

        Date printTime = new Date(System.currentTimeMillis());
        Date expiredTime = new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(8));


        tvDateTime.setText(dateFormat.format(printTime));
        tvDateExpired.setText(dateFormat.format(expiredTime));
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
        Fish fish = new Fish();
        fish.setEndDate(tvDateExpired.getText().toString());
        fish.setStartDate(tvDateExpired.getText().toString());
        fish.setMass(Integer.parseInt(etWeight.getText().toString()));
        fish.setName("Losos");
        pushInformationToFireBase(fish);
        finish();
    }


    private void initComponents(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
    }

    private void pushInformationToFireBase(Fish fish){
        ref.child("Fish Information").child(location).push().setValue(fish);
    }
}