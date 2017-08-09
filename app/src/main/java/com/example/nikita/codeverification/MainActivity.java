package com.example.nikita.codeverification;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.activity_main_txtgeneratecode)
    TextView txtGenerateCode;
    @BindView(R.id.activity_main_edentercode)
    EditText edEnterCode;
    @BindView(R.id.activity_main_btnsubmit)
    Button btnSubmit;
    @BindView(R.id.activity_main_txtmsg)
    TextView txtMsg;
    @BindView(R.id.activity_main_imgrefresh)
    ImageView imgRefresh;
    String strEnterCode, strGeneratedCode;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTimer();
        getSaltString();
        click();
    }

    // TODO: 9/8/17 get the alphanumeric string
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 16) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        Log.d("ANS", "@@" + saltStr);
        txtGenerateCode.setText(saltStr);
        return saltStr;

    }

    private void setTimer() {
        edEnterCode.setText("");
        timer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                edEnterCode.setVisibility(View.VISIBLE);
                txtMsg.setText("" + millisUntilFinished / 1000);
                txtMsg.setTextColor(Color.BLACK);
            }

            public void onFinish() {
                edEnterCode.setText("");
                edEnterCode.setVisibility(View.GONE);
                txtMsg.setText(getResources().getString(R.string.timeout));
                txtMsg.setTextColor(Color.BLACK);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_btnsubmit:
                getValues();
                if (strEnterCode.length() > 0) {
                    if (strGeneratedCode.equalsIgnoreCase(strEnterCode)) {
                        txtMsg.setText(getResources().getString(R.string.sucess));
                        txtMsg.setTextColor(Color.GREEN);
                        timer.cancel();
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
