package com.easyswitch.gosushi.dialog;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.view.HomeActivity;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dialog);
        ButterKnife.bind(this);

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
        finish();
    }
}