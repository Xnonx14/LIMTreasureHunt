package com.example.kevin.limtreasurehunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonOnClick(View v){
        System.out.println("kevin is the best!!! amazing");
    }

    public void goToBroken(View v){
        setContentView(R.layout.screen2);
    }

    public void goToHome(View v){
        setContentView(R.layout.activity_main);
    }

    public void goToFirst(View v) { setContentView(R.layout.screen3);}
}
