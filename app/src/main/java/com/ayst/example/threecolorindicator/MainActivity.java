package com.ayst.example.threecolorindicator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ayst.view.ThreeColorIndicator;

public class MainActivity extends AppCompatActivity {

    private ThreeColorIndicator mThreeColorIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mThreeColorIndicator = (ThreeColorIndicator) findViewById(R.id.indicator);
        mThreeColorIndicator.setValue(80);
    }
}
