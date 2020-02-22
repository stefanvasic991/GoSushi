package com.easyswitch.gosushi.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bxl.config.editor.BXLConfigLoader;
import com.easyswitch.gosushi.R;
import com.easyswitch.gosushi.adapter.ProductAdapter;
import com.easyswitch.gosushi.bixolonPrinter.PrinterConnectActivity;
import com.easyswitch.gosushi.bixolonPrinter.PrinterControl.BixolonPrinter;
import com.easyswitch.gosushi.dialog.SendDialog;
import com.easyswitch.gosushi.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    private static BixolonPrinter bxlPrinter;
    private int portType = BXLConfigLoader.DEVICE_BUS_BLUETOOTH;

    @BindView(R.id.rvFish)
    RecyclerView rvFish;

    List<Product> productList = new ArrayList<>();
    ProductAdapter adapter;
    private String location;
    private String logicalName = "SPP-R200II";
    private String address = "74:F0:7D:E3:8D:E2";

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

        final int ANDROID_NOUGAT = 24;
        if(Build.VERSION.SDK_INT >= ANDROID_NOUGAT) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        bxlPrinter = new BixolonPrinter(this);

        Thread.setDefaultUncaughtExceptionHandler(new AppUncaughtExceptionHandler());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.printer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id  == R.id.print) {
//            Intent i = new Intent(this, PrinterConnectActivity.class);
//                startActivityForResult(i, 200);
            connectPrinter();
        }
        return super.onOptionsItemSelected(item);
    }

    public static BixolonPrinter getPrinterInstance() {
        return bxlPrinter;
    }

    public class AppUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, final Throwable ex) {
            ex.printStackTrace();

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    private void connectPrinter() {

        if (HomeActivity.getPrinterInstance().printerOpen(portType, logicalName, address, true)) {
            Toast.makeText(this, "Štampač je povezan!", Toast.LENGTH_LONG).show();
//                setResult(RESULT_OK);
//                finish();
        } else {

            Toast.makeText(this, "Došlo je do greške. Proverite da li je bluetooth uključen.", Toast.LENGTH_LONG).show();
        }
    }
}
