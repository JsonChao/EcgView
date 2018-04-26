package com.chao.json.ecgview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private EcgView mEcgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEcgView = findViewById(R.id.ecg_view);
    }

    public void startEcg(View view) {
        mEcgView.setRandomNumber();
        mEcgView.startEcgView();
    }

    public void endEcg(View view) {
        mEcgView.stopEcgView();
    }

}
