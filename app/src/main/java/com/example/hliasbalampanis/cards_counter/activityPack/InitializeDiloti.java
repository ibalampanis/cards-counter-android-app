package com.example.hliasbalampanis.cards_counter.activityPack;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hliasbalampanis.cards_counter.activityPack.*;
import com.example.hliasbalampanis.cards_counter.R;

import java.io.IOException;


public class InitializeDiloti extends AppCompatActivity {

    protected Button b_save_changes;
    protected EditText et_name_team_a,et_name_team_b,et_num_rounds;
    protected EditText et_points_team_a,et_points_team_b;
    protected CheckBox cb_init_a,cb_init_b;
    private int points1,points2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_diloti);

        b_save_changes = (Button) findViewById(R.id.b_save_changes);
        et_name_team_a = (EditText) findViewById(R.id.et_name_team_a);
        et_name_team_b = (EditText) findViewById(R.id.et_name_team_b);
        et_points_team_a = (EditText) findViewById(R.id.et_points_team_a);
        et_points_team_b = (EditText) findViewById(R.id.et_points_team_b);
        cb_init_a = (CheckBox) findViewById(R.id.cb_init_a);
        cb_init_b = (CheckBox) findViewById(R.id.cb_init_b);
        /** By default **/
        DilotiActivity.points_player1.empty();
        DilotiActivity.points_player2.empty();
        et_name_team_a.setText(DilotiActivity.editText_P1.getText().toString());
        et_name_team_b.setText(DilotiActivity.editText_P2.getText().toString());
        cb_init_a.setText(et_name_team_a.getText().toString());
        cb_init_b.setText(et_name_team_b.getText().toString());
        giveDefaultRow();
    }


    public void clickOnSaveChanges(View v){
        this.points1 = Integer.parseInt(et_points_team_a.getText().toString());
        this.points2 = Integer.parseInt(et_points_team_b.getText().toString());
        int comp_value = this.points1 + this.points2;
        int value = comp_value % 2;

        if (comp_value == 11 || value == 1 ) {
            /** Team names **/
            DilotiActivity.editText_P1.setText(et_name_team_a.getText().toString());
            DilotiActivity.editText_P2.setText(et_name_team_b.getText().toString());

            /** Push into Stacks **/
            DilotiActivity.points1 = this.points1;
            DilotiActivity.points2 = this.points2;
            DilotiActivity.points_player1.push(DilotiActivity.points1);
            DilotiActivity.points_player2.push(DilotiActivity.points2);
            /** Display this values **/
            DilotiActivity.txtPn1.setText(et_points_team_a.getText().toString());
            DilotiActivity.txtPn2.setText(et_points_team_b.getText().toString());
            DilotiActivity.tv_num_rounds.setEnabled(false);
            giveRow();
            onBackPressed();
        } else {
            playWrongSound();
            Toast.makeText(this,"Λάθος τιμές!", Toast.LENGTH_SHORT).show();
        }

    }

    public void giveDefaultRow(){
        if(DilotiActivity.cb_row_p1.isChecked() && !DilotiActivity.cb_row_p2.isChecked()){
            cb_init_a.setChecked(true);
            cb_init_b.setChecked(false);
        }
        else if (!DilotiActivity.cb_row_p1.isChecked() && DilotiActivity.cb_row_p2.isChecked()){
            cb_init_b.setChecked(false);
            cb_init_b.setChecked(true);
        }
        else{
            cb_init_a.setChecked(false);
            cb_init_b.setChecked(false);
        }
    }

    public int giveRow(){
        if(cb_init_a.isChecked() && !cb_init_b.isChecked()){
            DilotiActivity.cb_row_p1.setChecked(true);
            DilotiActivity.cb_row_p2.setChecked(false);
        }
        else if(!cb_init_a.isChecked() && cb_init_b.isChecked()){
            DilotiActivity.cb_row_p1.setChecked(false);
            DilotiActivity.cb_row_p2.setChecked(true);
        }
        else return 0;

        return 1;
    }

    public void playWrongSound(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.wrong);
        mediaPlayer.start();
    }


}
