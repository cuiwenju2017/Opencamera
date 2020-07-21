package com.cwj.opencamera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.atech.glcamera.interfaces.FileCallback;
import com.atech.glcamera.interfaces.FilteredBitmapCallback;
import com.atech.glcamera.utils.FileUtils;
import com.atech.glcamera.utils.FilterFactory;
import com.atech.glcamera.views.GLCameraView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(view -> {
            if (checkPemission() && checkCameraHardware(MainActivity.this)) {
                startActivity(new Intent(this, OpenCameraActivity.class));
            } else {
                Toast.makeText(MainActivity.this, "permission request", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        // this device has a camera
        // no camera on this device
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);

    }

    private int read;
    private int write;
    private int camera;
    private int record;
    public static final int CameraPermision = 100;

    private boolean checkPemission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            read = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            write = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            camera = checkSelfPermission(Manifest.permission.CAMERA);
            record = checkSelfPermission(Manifest.permission.RECORD_AUDIO);

            if (read != PackageManager.PERMISSION_GRANTED || write != PackageManager.PERMISSION_GRANTED
                    || camera != PackageManager.PERMISSION_GRANTED || record != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                }, CameraPermision);
                return false;
            }
        }
        return true;
    }
}