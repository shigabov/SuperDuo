package it.jaschke.alexandria.services;

/**
 * Created by Артем on 14.10.2015.
 */

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.jaschke.alexandria.MainActivity;
import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class SimpleScannerActivity extends ActionBarActivity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        //BarcodeFormat ean13 = BarcodeFormat.EAN13;

        mScannerView.setAutoFocus(true);
        mScannerView.setFlash(true);

        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.ISBN10);
        formats.add(BarcodeFormat.EAN13);
        formats.add(BarcodeFormat.ISBN13);
        mScannerView.setFormats(formats);

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("CONTENTS",rawResult.getContents());
        //Toast.makeText(getApplicationContext(),rawResult.getBarcodeFormat().getName(),Toast.LENGTH_LONG).show();
        intent.putExtra("FORMAT",rawResult.getBarcodeFormat().getName());
        setResult(RESULT_OK, intent);
        finish();
    }
}