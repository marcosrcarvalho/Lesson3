package com.marcoscarvalho.runtimepermissions;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int RUNTIME_PERMISSION_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonRequestPermission = (Button) findViewById(R.id.buttonRequestPermission);

        buttonRequestPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCameraAllowed()) {
                    Toast.makeText(view.getContext(), "You already have the permission to access Camera", Toast.LENGTH_LONG).show();
                    return;
                }

                requestCameraPermission();
            }
        });
    }

    private void requestCameraPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
            showAlertDialog();
        }

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, RUNTIME_PERMISSION_CODE);
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Runtime Permission");

        alertDialogBuilder.setMessage("This is tutorial for Runtime Permission. This needs permission of accessing your device Camera." + "Please Grant the Permission").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    private boolean isCameraAllowed(){
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if(result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == RUNTIME_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission granted now you can use the camera " , Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"Oops you just denied the permission" , Toast.LENGTH_LONG).show();
            }
        }
    }
}
