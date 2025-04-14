package com.example.hliasbalampanis.cards_counter.activityPack;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hliasbalampanis.cards_counter.R;


public class MainActivity extends AppCompatActivity {

    protected Button b_mp,b_dhl,b_tichu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (shouldAskPermissions()) askPermissions();

        b_mp = (Button) findViewById(R.id.b_mp);
        b_dhl = (Button) findViewById(R.id.b_dhl);
        b_tichu = (Button) findViewById(R.id.b_tichu);

    }

    public void ClickOnMpirimpa(View v){
        Intent mpirimpa = new Intent(this, MpirimpaActivity.class);
        startActivity(mpirimpa);
    }

    public void ClickOnTichu(View v){
        Intent tichu = new Intent(this, TichuActivity.class);
        startActivity(tichu);
    }

    public void ClickOnDiloti(View v){
        Intent diloti = new Intent(this, DilotiActivity.class);
        startActivity(diloti);
    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Είστε σίγουρος πως θέλετε να κλείσετε την εφαρμογή;")
                .setCancelable(false)
                .setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Οχι", null)
                .show();
    }
}
