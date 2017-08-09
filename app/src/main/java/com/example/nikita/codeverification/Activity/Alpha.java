package com.example.nikita.codeverification.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikita.codeverification.R;
import com.example.nikita.codeverification.Utils.Constants;
import com.example.nikita.codeverification.Utils.Utils;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Alpha extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.activity_main_txtgeneratecode)
    TextView txtGenerateCode;
    @BindView(R.id.activity_main_edentercode)
    EditText edEnterCode;
    @BindView(R.id.activity_main_btnsubmit)
    Button btnSubmit;
    @BindView(R.id.activity_main_txtmsg)
    TextView txtMsg;
    @BindView(R.id.activity_main_txthighscore)
    TextView txtHighScore;
    @BindView(R.id.activity_main_imgrefresh)
    ImageView imgRefresh;
    public String strEnterCode, strGeneratedCode;
    public CountDownTimer timer;
    public String remainTime;
    public int wonTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTimer();
        getSaltString();
        getScore();
        click();
    }

    private void getScore() {
        // TODO: 9/8/17 temporary score
        int levelScore = 10;
        String totalTime = Utils.ReadSharedPreference(this, Constants.TOTAL_TIME);
        String scoreTime = Utils.ReadSharedPreference(this, Constants.REMAIN_TIME);
        wonTime = Integer.parseInt(totalTime) - (Integer.parseInt(scoreTime));
//        int score = Math.max(0, wonTime) * levelScore;
        txtHighScore.setText("Win Timing : " + wonTime);
        edEnterCode.setText("");
    }

    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 16) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        txtGenerateCode.setText(saltStr);
        edEnterCode.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.VISIBLE);
        return saltStr;
    }

    private void setTimer() {
        edEnterCode.setText("");
        Utils.WriteSharedPreference(this, Constants.TOTAL_TIME, "30");
        timer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                edEnterCode.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
                txtMsg.setVisibility(View.VISIBLE);
                txtMsg.setText("" + millisUntilFinished / 1000);
                txtMsg.setTextColor(Color.BLACK);
                remainTime = String.valueOf(millisUntilFinished / 1000);
                Utils.WriteSharedPreference(Alpha.this, Constants.REMAIN_TIME, remainTime);
            }

            public void onFinish() {
                edEnterCode.setText("");
                edEnterCode.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                txtMsg.setVisibility(View.GONE);
                showLoseAlert();
            }
        }.start();
    }

    private void click() {
        btnSubmit.setOnClickListener(this);
        imgRefresh.setOnClickListener(this);
    }

    private void getValues() {
        strGeneratedCode = txtGenerateCode.getText().toString().trim();
        strEnterCode = edEnterCode.getText().toString().trim();
    }

    private void showWinAlert() {
        timer.cancel();
        getScore();
        final String highScore = Utils.ReadSharedPreference(Alpha.this,Constants.HIGH_SCORE);
        edEnterCode.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulations!!!")
                .setMessage("You have Successfully Clear this Level." + "\n" +
                        "Now go to the Next Level by Click on Refresh button." + "\n" +
                        "Share Your Score with Friends.").setCancelable(true)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent iBack = new Intent(Alpha.this, Home.class);
                        startActivity(iBack);
                    }
                })
                .setNeutralButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "My Score is : " +highScore);
                        sendIntent.setType("text/plain");
                        startActivity(Intent.createChooser(sendIntent, "Share Via"));
                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }

    private void showLoseAlert() {
        timer.cancel();
        getScore();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opss!!!")
                .setMessage("Sorry!!,You have not clear this level." +
                        "Please Try Again.").setCancelable(true)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent iBack = new Intent(Alpha.this, Home.class);
                        startActivity(iBack);
                    }
                })
                .setPositiveButton("Play", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        getSaltString();
                        setTimer();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_btnsubmit:
                getValues();
                if (strEnterCode.length() > 0) {
                    if (strGeneratedCode.equalsIgnoreCase(strEnterCode)) {
                        showWinAlert();
                        Utils.WriteSharedPreference(Alpha.this, Constants.HIGH_SCORE, String.valueOf(wonTime));
                    } else {
                        Toast.makeText(this, "Opss!! Please Enter Valid Number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please Enter Number!!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.activity_main_imgrefresh:
                getSaltString();
                timer.cancel();
                setTimer();
                break;
        }
    }
}
