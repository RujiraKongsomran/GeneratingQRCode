package com.rujirakongsomran.generatingqrcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.rujirakongsomran.generatingqrcode.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText != null && !newText.isEmpty()) {
                    // Initialize multi format writer
                    MultiFormatWriter writer = new MultiFormatWriter();
                    try {
                        // Initialize bit matrix
                        BitMatrix matrix = writer.encode(newText,
                                BarcodeFormat.QR_CODE,
                                350,
                                350);
                        // Initialize barcode encoder
                        BarcodeEncoder encoder = new BarcodeEncoder();
                        // Initialize bitmap
                        Bitmap bitmap = encoder.createBitmap(matrix);
                        // Set bitmap on ImageView
                        binding.ivQRCode.setImageBitmap(bitmap);
                        // Initialize input manager
                        InputMethodManager methodManager = (InputMethodManager) getSystemService(
                                Context.INPUT_METHOD_SERVICE
                        );
                        // Hide Soft keyboard
                        methodManager.hideSoftInputFromWindow(binding.searchView.getApplicationWindowToken(),
                                0);


                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                } else {
                    // Clear ImageView
                    binding.ivQRCode.setImageResource(0);
                }
                return false;
            }
        });
    }
}