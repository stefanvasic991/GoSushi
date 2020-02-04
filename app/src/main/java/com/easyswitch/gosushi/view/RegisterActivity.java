package com.easyswitch.gosushi.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.adapter.LocationAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.tilLocation)
    TextInputLayout tilLocation;
    @BindView(R.id.etLocation)
    TextInputEditText etLocation;
    @BindView(R.id.tilAdress)
    TextInputLayout tilAdress;
    @BindView(R.id.etAdress)
    TextInputEditText etAdress;
    @BindView(R.id.tilCity)
    TextInputLayout tilCity;
    @BindView(R.id.etCity)
    TextInputEditText etCity;
    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.tilConfirmPassword)
    TextInputLayout tilConfirmPassword;
    @BindView(R.id.etConfirmPassword)
    TextInputEditText ettilConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
