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
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.example.hliasbalampanis.cards_counter.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Stack;

public class TichuActivity extends AppCompatActivity {

    private Stack <Integer> points_player1 = new Stack <>();
    private Stack <Integer> points_player2 = new Stack <>();
    private EditText editText_P1,editText_P2,editTextPN1,editTextPN2;
    private TextView txtPn1,txtPn2,tv_num_rounds;
    private Button b_ok,b_undo,b_rst,b_back,b_rules;
    private int points1,points2,numRounds=1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tichu);

        if (shouldAskPermissions()) askPermissions();

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

        /** Initialize **/
        txtPn1.setText(Integer.toString(0));
        txtPn2.setText(Integer.toString(0));
        editTextPN1.setText("");
        editTextPN2.setText("");
        points_player1.push(0);
        points_player2.push(0);
        points1 = 0;
        points2 = 0;
        tv_num_rounds.setText("Τρέχων γύρος: " + numRounds);
    }

    public void clickOnOK(View v){
        int sum_value = Integer.parseInt(editTextPN1.getText().toString())+Integer.parseInt(editTextPN2.getText().toString());
        int value = sum_value % 100;
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
        if( sum_value == 100 || value == 0 || sum_value == 0) {
            b_rst.setEnabled(true);
            b_undo.setEnabled(true);
            points1 =  points_player1.push(points1 + Integer.parseInt(editTextPN1.getText().toString()));
            points2 =  points_player2.push(points2 + Integer.parseInt(editTextPN2.getText().toString()));
            txtPn1.setText(Integer.toString(points1));
            txtPn2.setText(Integer.toString(points2));
            editTextPN1.setText("");
            editTextPN2.setText("");
            numRounds++;
            tv_num_rounds.setText("Τρέχων γύρος: " + numRounds);
        }
        else{
            editTextPN1.setText("");
            editTextPN2.setText("");
            playWrongSound();
            Toast.makeText(this, "Λάθος τιμές!", Toast.LENGTH_SHORT).show();
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
            numRounds--;
        }else if (points_player1.size() == 1){
            numRounds=1;
            txtPn1.setText(Integer.toString(points_player1.peek()));
            txtPn2.setText(Integer.toString(points_player2.peek()));
            points1 =  points_player1.peek();
            points2 =  points_player2.peek();
            b_undo.setEnabled(false);
        }
    }

    public void clickOnRst(View v){
        numRounds=1;
        editTextPN1.setText("0");
        editTextPN2.setText("0");
        points1 = Integer.parseInt(editTextPN1.getText().toString());
        points2 = Integer.parseInt(editTextPN2.getText().toString());
        points_player1.empty();
        points_player2.empty();
        txtPn1.setText(Integer.toString(0));
        txtPn2.setText(Integer.toString(0));
        b_rst.setEnabled(false);
    }

    public void clickOnBack(View v){
        new android.support.v7.app.AlertDialog.Builder(this)
                .setMessage("Είστε σίγουροι;")
                .setCancelable(false)
                .setPositiveButton("Ναι", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TichuActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Όχι", null)
                .show();

    }

    public  void clickOnRules(View v){
        Intent tichuRules = new Intent(this, TichuRules.class);
        startActivity(tichuRules);
    }

    public void clickOnScreenshot(View v){
        takeScreenshot();
        playScreenshotSound();
    }

    public void onBackPressed(){
        clickOnBack(null);
    }

    public void clickOnLoad(View v)  throws IOException {
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

