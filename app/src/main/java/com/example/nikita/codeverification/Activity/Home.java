package com.example.nikita.codeverification.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nikita.codeverification.Activity.Alpha;
import com.example.nikita.codeverification.Activity.Numeric;
import com.example.nikita.codeverification.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.activity_home_btnalpha)
    Button btnAlpha;
    @BindView(R.id.activity_home_btnalpha_numeric)
    Button btnAlphaNumeric;
    @BindView(R.id.activity_home_btnnumeric)
    Button btnNumeric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        click();
    }

    private void click() {
        btnAlpha.setOnClickListener(this);
        btnAlphaNumeric.setOnClickListener(this);
        btnNumeric.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_home_btnalpha:
                Intent iAlpha = new Intent(Home.this, Alpha.class);
                startActivity(iAlpha);
                break;

            case R.id.activity_home_btnalpha_numeric:
                Intent iNumeric = new Intent(Home.this, MainActivity.class);
                startActivity(iNumeric);
                break;

            case R.id.activity_home_btnnumeric:
                Intent iAlphaNum = new Intent(Home.this, Numeric.class);
                startActivity(iAlphaNum);
                break;
        }
    }
}
