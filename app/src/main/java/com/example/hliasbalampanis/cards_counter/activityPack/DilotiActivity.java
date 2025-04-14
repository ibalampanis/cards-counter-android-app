package com.example.hliasbalampanis.cards_counter.activityPack;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import android.view.View;
import android.widget.Toast;
import com.example.hliasbalampanis.cards_counter.R;

public class DilotiActivity extends AppCompatActivity {

    protected static Stack <Integer> points_player1 = new Stack <>();
    protected static Stack <Integer> points_player2 = new Stack <>();
    protected static EditText editText_P1,editText_P2,editTextPN1,editTextPN2;
    protected static TextView txtPn1,txtPn2,tv_num_rounds;
    protected Button b_ok,b_undo,b_rst,b_back,b_rules,b_load,b_init_values;
    protected static CheckBox cb_row_p1,cb_row_p2;
    protected static int points1,points2,numRounds;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diloti);

        if (shouldAskPermissions()) askPermissions();

        b_init_values = (Button) findViewById(R.id.b_init_values);
        cb_row_p1  = (CheckBox) findViewById(R.id.cb_row_p1);
        cb_row_p2  = (CheckBox) findViewById(R.id.cb_row_p2);
        editText_P1 = (EditText) findViewById(R.id.editText_P1);
        editText_P2 = (EditText) findViewById(R.id.editText_P2);
        editTextPN1 = (EditText) findViewById(R.id.editTextPN1);
        editTextPN2 = (EditText) findViewById(R.id.editTextPN2);
        txtPn1  = (TextView) findViewById(R.id.txtPn1);
        txtPn2  = (TextView) findViewById(R.id.txtPn2);
        tv_num_rounds = (TextView) findViewById(R.id.tv_num_rounds);
        b_ok    = (Button) findViewById(R.id.b_ok);
        b_undo  = (Button) findViewById(R.id.b_undo);
        b_rst   = (Button) findViewById(R.id.b_rst);
        b_back  = (Button) findViewById(R.id.b_back_r);
        b_rules = (Button) findViewById(R.id.b_rules);
        b_load  = (Button) findViewById(R.id.b_load);

        /** Initialize **/
        tv_num_rounds.setText("Τρέχων γύρος: " + numRounds);
        txtPn1.setText(Integer.toString(0));
        txtPn2.setText(Integer.toString(0));
        editTextPN1.setText("");
        editTextPN2.setText("");
        points_player1.push(0);
        points_player2.push(0);
        points1 = 0;
        points2 = 0;
        numRounds = 1;
        cb_row_p1.setChecked(true);
        cb_row_p2.setChecked(false);
        b_load.setClickable(false);
        b_load.setEnabled(false);
    }

    public void clickOnOK(View v){
        int comp_value = Integer.parseInt(editTextPN1.getText().toString())+Integer.parseInt(editTextPN2.getText().toString());
        int value = comp_value % 2;
        editTextPN1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                b_ok.setEnabled(TextUtils.isEmpty(editTextPN1.getText().toString()));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                b_ok.setEnabled(!TextUtils.isEmpty(editTextPN1.getText().toString()));
            }
        });
        editTextPN2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                b_ok.setEnabled(TextUtils.isEmpty(editTextPN2.getText().toString()));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                b_ok.setEnabled(!TextUtils.isEmpty(editTextPN2.getText().toString()));
            }
        });
        if (comp_value == 11 || value == 1 ){
            b_rst.setEnabled(true);
            b_undo.setEnabled(true);
            points1 =  points_player1.push(points1 + Integer.parseInt(editTextPN1.getText().toString()));
            points2 =  points_player2.push(points2 + Integer.parseInt(editTextPN2.getText().toString()));
            txtPn1.setText(Integer.toString(points1));
            txtPn2.setText(Integer.toString(points2));
            editTextPN1.setText("");
            editTextPN2.setText("");
            changeRow();
            numRounds++;
            tv_num_rounds.setText("Τρέχων γύρος: " + numRounds);
        }
        else{
            editTextPN1.setText("");
            editTextPN2.setText("");
            playWrongSound();
            Toast.makeText(this,"Λάθος τιμές!", Toast.LENGTH_SHORT).show();
        }
        if(points1 >= 61 || points2 >= 61){
            playVictorySound();
        }
    }

    public void clickOnUndo(View v) {
        if (points_player1.size() != 1){
            points_player1.pop();
            points_player2.pop();
            txtPn1.setText(Integer.toString(points_player1.peek()));
            txtPn2.setText(Integer.toString(points_player2.peek()));
            points1 =  points_player1.peek();
            points2 =  points_player2.peek();
            changeRow();
            numRounds--;
        }
        else if (points_player1.size() == 1){
            numRounds = 1;
            txtPn1.setText(Integer.toString(points_player1.peek()));
            txtPn2.setText(Integer.toString(points_player2.peek()));
            points1 =  points_player1.peek();
            points2 =  points_player2.peek();
            b_undo.setEnabled(false);
        }
    }

    public void clickOnRst(View v){
        numRounds = 1;
        tv_num_rounds.setText("Τρέχων Γύρος: " + numRounds);
        editTextPN1.setText("0");
        editTextPN2.setText("0");
        points1 = Integer.parseInt(editTextPN1.getText().toString());
        points2 = Integer.parseInt(editTextPN2.getText().toString());
        points_player1.empty();
        points_player2.empty();
        txtPn1.setText(Integer.toString(0));
        txtPn2.setText(Integer.toString(0));
        b_rst.setEnabled(false);
        cb_row_p1.setChecked(true);
        cb_row_p2.setChecked(false);
    }

    public void clickOnInitialize(View v){
        Intent initialize = new Intent(this,InitializeDiloti.class);
        startActivity(initialize);
    }

    public void clickOnBack(View v){
        new android.support.v7.app.AlertDialog.Builder(this)
                .setMessage("Είστε σίγουροι;")
                .setCancelable(false)
                .setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DilotiActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Όχι", null)
                .show();

    }

    public void clickOnLoad(View v)  throws IOException{
    }

    public void clickOnRules(View v){
        Intent dilotiRules = new Intent(this, DilotiRules.class);
        startActivity(dilotiRules);
    }

    public void clickOnScreenshot(View v){
        takeScreenshot();
        playScreenshotSound();
    }

    public void onBackPressed(){
        clickOnBack(null);
    }

    public void changeRow(){
        if(cb_row_p1.isChecked()){
            cb_row_p1.setChecked(false);
            cb_row_p2.setChecked(true);
        }
        else if(cb_row_p2.isChecked()){
            cb_row_p2.setChecked(false);
            cb_row_p1.setChecked(true);
        }
    }

    protected void onDestroy(){
        super.onDestroy();
    }

    protected void onStop(){
        super.onStop();
    }

    protected void onPause(){
        super.onPause();
    }

    public void playVictorySound(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.victory);
        mediaPlayer.start();
    }

    public void playWrongSound(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.wrong);
        mediaPlayer.start();
    }

    public void playScreenshotSound(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.screenshot);
        mediaPlayer.start();
    }

    public void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        String FILENAME = "screenshot" + now + ".jpg";

        String folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Cards Counter Screenshots";
        File imageFolder = new File(folderPath);

        if (!imageFolder.exists()){
            imageFolder.mkdirs();
        }
        String imagePath = folderPath + "/" + FILENAME;

        View v = getWindow().getDecorView().getRootView();
        v.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        OutputStream out = null;
        File imageFile = new File(imagePath);
        try {
            out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));
            Toast.makeText(this, "Το screenshot αποθηκεύτηκε!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (out != null){
                    out.close();
                }
            } catch (Exception exc){
                exc.printStackTrace();
            }
        }
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

}