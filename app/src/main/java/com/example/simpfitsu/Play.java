package com.example.simpfitsu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static androidx.core.graphics.drawable.IconCompat.getResources;

public class Play extends AppCompatActivity {
    String[] option1 = getResources().getStringArray(R.array.optionstory1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }
}