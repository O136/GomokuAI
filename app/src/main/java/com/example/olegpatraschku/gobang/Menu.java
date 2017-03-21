package com.example.olegpatraschku.gobang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by oleg on 18.08.16.
 */
public class Menu extends AppCompatActivity {
    private boolean black, onePlayer;
    private Button mStart;
    private RadioGroup mColor, mPlayerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        mPlayerCount = (RadioGroup)findViewById(R.id.playersRadioGroup);
        mColor = (RadioGroup)findViewById(R.id.colorRadioGroup);
        mStart = (Button)findViewById(R.id.startButton);

        mColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                black = mColor.getCheckedRadioButtonId() == R.id.radio_button_black;
            }
        });

        mPlayerCount.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                onePlayer = mPlayerCount.getCheckedRadioButtonId() == R.id.radio_button_one;
            }
        });

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GameController.class);
                i.putExtra(getString(R.string.black_color_option), black);
                i.putExtra(getString(R.string.one_player_option), onePlayer);
                startActivity(i);
            }
        });
    }
}
