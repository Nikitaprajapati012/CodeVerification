package com.example.nikita.codeverification.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.nikita.codeverification.R;
import com.example.nikita.codeverification.Utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.activity_home_btnalpha)
    Button btnAlpha;
    @BindView(R.id.activity_home_btnalpha_numeric)
    Button btnAlphaNumeric;
    @BindView(R.id.activity_home_btnnumeric)
    Button btnNumeric;
    private static final int THEME_BLUE = Menu.FIRST;
    private static final int THEME_RED = Menu.FIRST + 1;
    private static final int THEME_PINK = Menu.FIRST + 2;
    private static final int THEME_NORMAL = Menu.FIRST + 3;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0, THEME_BLUE, Menu.NONE, R.string.blue).setIcon(R.drawable.ic_blue);
        menu.add(0, THEME_RED, Menu.NONE, R.string.red).setIcon(R.drawable.ic_red);
        menu.add(0, THEME_PINK, Menu.NONE, R.string.pink).setIcon(R.drawable.ic_pink);
        menu.add(0, THEME_NORMAL, Menu.NONE, R.string.normal).setIcon(R.drawable.ic_gray);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case THEME_BLUE:
                changeBlueTheme();
                break;
            case THEME_RED:
                changeRedTheme();
                break;
            case THEME_PINK:
                changePinkTheme();
                break;
            case THEME_NORMAL:
                changeNormalTheme();
                break;
        }
        return false;
    }

    // TODO: 10/8/17 theme change 
    private void changeNormalTheme() {
        Utils.changeToTheme(this, Utils.THEME_DEFAULT);
    }

    private void changePinkTheme() {
        Utils.changeToTheme(this, Utils.THEME_PINK);
    }

    private void changeRedTheme() {
        Utils.changeToTheme(this, Utils.THEME_RED);
    }

    private void changeBlueTheme() {
        Utils.changeToTheme(this, Utils.THEME_BLUE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
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
