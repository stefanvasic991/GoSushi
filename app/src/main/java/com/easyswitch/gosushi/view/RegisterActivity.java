package com.easyswitch.gosushi.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.adapter.LocationAdapter;
import com.easyswitch.gosushi.model.RestourantModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    //    @BindView(R.id.tilConfirmPassword)
//    TextInputLayout tilConfirmPassword;
//    @BindView(R.id.etConfirmPassword)
//    TextInputEditText ettilConfirmPassword;
    @BindView(R.id.txt_register)
    TextView txt_register;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser firebaseUser;
    private DatabaseReference ref;
    private RestourantModel restourantModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initComponents();
        addListeners();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initComponents() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        restourantModel = new RestourantModel();
    }

    private void addListeners() {
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushInformationToFireBase();
                backToHome();
            }
        });
    }

    private void backToHome() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void pushInformationToFireBase() {
        restourantModel.setLocation(etLocation.getText().toString());
        restourantModel.setCity(etCity.getText().toString());
        restourantModel.setAdress(etAdress.getText().toString());
        pushInformationToDataBase(restourantModel);
    }

    private void pushInformationToDataBase(RestourantModel restourantModel) {
        ref.child("Restourant Information").child(restourantModel.getLocation()).setValue(restourantModel);
    }
}
