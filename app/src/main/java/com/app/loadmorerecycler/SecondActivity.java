package com.app.loadmorerecycler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
    private Button btMain;
    private ToggleButton mToggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btMain= (Button) findViewById(R.id.bt_main);
        mToggleButton= (ToggleButton) findViewById(R.id.toggle_btn);

        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this,MainActivity.class));
            }
        });

        mToggleButton.setOnToggledListener(new ToggleListener() {
            @Override
            public void onToggled(boolean isOpen) {

            }
        });
    }
}
