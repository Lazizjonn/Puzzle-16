package uz.gita.puzzle;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uz.gita.local.SharePref;

public class StatisticsActivity extends AppCompatActivity {
    TextView first, second, third, fourth, fifth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        SharePref sharePref = SharePref.getPref(this);

        first = findViewById(R.id.first);        second = findViewById(R.id.second);
        third = findViewById(R.id.third);        fourth = findViewById(R.id.fourth);
        fifth = findViewById(R.id.fifth);

        if (sharePref.getFirst() == Integer.MAX_VALUE) {
            first.setText("First place : " + "~");
        } else first.setText("First place : " + sharePref.getFirst());

        if (sharePref.getSecond() == Integer.MAX_VALUE) {
            second.setText("Second place : " + "~");
        } else second.setText("Second place : " + sharePref.getSecond());

        if (sharePref.getThird() == Integer.MAX_VALUE) {
            third.setText("Third place : " + "~");
        } else third.setText("Third place : " + sharePref.getThird());

        if (sharePref.getFourth() == Integer.MAX_VALUE) {
            fourth.setText("Fourth place : " + "~");
        } else fourth.setText("Fourth place : " + sharePref.getFourth());

        if (sharePref.getFifth() == Integer.MAX_VALUE) {
            fifth.setText("Fifth place : " + "~");
        } else fifth.setText("Fifth place : " + sharePref.getFifth());

        findViewById(R.id.back_button).setOnClickListener(v -> {
            onBackPressed();
        });
    }
}