package uz.gita.puzzle;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import uz.gita.local.SharePref;

public class WinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);

        AppCompatTextView winnerCountText = findViewById(R.id.winner_count);
        AppCompatTextView winnerTimeText = findViewById(R.id.winner_time);

        Bundle bundle = getIntent().getExtras();


        int winnerCount = bundle.getInt("WinnerCount", Integer.MAX_VALUE);
        int winnerTime = bundle.getInt("WinnerTime", 0);

        SharePref.getPref(this).saveResults(winnerCount);

        winnerCountText.setText(winnerCount + " times moved");
        winnerTimeText.setText(winnerTime + " second spend");

        findViewById(R.id.home_button).setOnClickListener(view -> {
            onBackPressed();
        });

        findViewById(R.id.statistics_go).setOnClickListener(v -> {
            Intent intent = new Intent(WinnerActivity.this, StatisticsActivity.class);
            finish();
            startActivity(intent);
        });

    }
}