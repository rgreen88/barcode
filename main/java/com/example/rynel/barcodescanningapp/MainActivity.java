package com.example.rynel.barcodescanningapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button scanBtn;
    private TextView formatTxt, contentTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        //calling instance of Timber first thing in onCreate
        Timber.plant();

        scanBtn = findViewById(R.id.scan_button);
        formatTxt = findViewById(R.id.scan_format);
        contentTxt = findViewById(R.id.scan_content);

        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //scan

        //timber debug log
        Timber.d(toString());

        if (v.getId() == R.id.scan_button) {

            IntentIntegrator scanIntegrator = new IntentIntegrator(this);

            scanIntegrator.initiateScan();

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result

            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            formatTxt.setText("Scanning " + scanFormat);
            contentTxt.setText("Result: " + scanContent);

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}


